package com.ohyeah5566.senaohw

import android.util.Log
import androidx.paging.*

interface MartRepository : Remote, MartDao

interface Remote {
    suspend fun fetchRemoteMart(): List<Mart>
}

class MartRepositoryImp(
    private val service: MartService,
    localMartDao: AppDatabase
) : MartRepository, MartDao by MartDao_Impl(localMartDao) {
    override suspend fun fetchRemoteMart(): List<Mart> {
        return try {
            service.getMartList().data
        } catch (ex:Exception){
            emptyList()
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
class MartRemoteMediator(
    private val repository: MartRepository
) : RemoteMediator<Int, Mart>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Mart>): MediatorResult {
        Log.d("", "Remote,${loadType}")
        return when (loadType) {
            LoadType.APPEND -> {
                val list = repository.fetchRemoteMart()
                repository.insertAll(list)
                if (list.size < 20) {
                    MediatorResult.Success(true)
                } else {
                    MediatorResult.Success(false)
                }
            }
            LoadType.REFRESH -> {
                MediatorResult.Success(false)
            }
            LoadType.PREPEND -> {
                MediatorResult.Success(true)
            }
        }
    }
}