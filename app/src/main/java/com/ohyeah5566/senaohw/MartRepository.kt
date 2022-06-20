package com.ohyeah5566.senaohw

import android.util.Log
import androidx.paging.*
import okio.IOException

interface MartRepository : Remote, MartDao

interface Remote {
    suspend fun fetchRemoteMart(): List<Mart>
}

class MartRepositoryImp(
    private val service: MartService,
    localMartDao: AppDatabase
) : MartRepository,
    MartDao by MartDao_Impl(localMartDao) //將MartDao的實作 委派給自動產出的MartDao_Impl, 沒編譯前MartDao_Impl會顯示紅字
{
    override suspend fun fetchRemoteMart(): List<Mart> {
        return service.getMartList().data
    }
}

//雖然目前的資料只有8筆 而且也不清楚next page的機制是如何運作
//但還是用RemoteMediator+room-paging 實現作業1.的Api取得資料和儲存資料
//也方便之後真的有大量資料需要讀取 可以快速的調整next key的邏輯
@OptIn(ExperimentalPagingApi::class)
class MartRemoteMediator(
    private val repository: MartRepository
) : RemoteMediator<Int, Mart>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Mart>): MediatorResult {
        Log.d("", "Remote,${loadType}")
        return when (loadType) {
            LoadType.APPEND -> {
                try {
                    val list = repository.fetchRemoteMart()
                    repository.insertAll(list)
                    //這邊假設一次回傳pageSize筆資料如果小於pageSize筆 表示沒更多資料了
                    if (list.size < state.config.pageSize) {
                        MediatorResult.Success(true)
                    } else {
                        MediatorResult.Success(false)
                    }
                } catch (ex: Exception) {
                    MediatorResult.Error(ex)
                }
            }
            //refresh跟prepend這次的作業用不到就return
            LoadType.REFRESH -> {
                MediatorResult.Success(false)
            }
            LoadType.PREPEND -> {
                MediatorResult.Success(true)
            }
        }
    }
}