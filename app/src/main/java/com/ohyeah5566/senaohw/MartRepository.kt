package com.ohyeah5566.senaohw

interface MartRepository {
    suspend fun loadList(): List<Mart>

    suspend fun loadListWithKeyword(keyword: String): List<Mart>
}

class MartRepositoryRemote(
    private val service: MartService,
    private val localMartDao: MartDao
) : MartRepository {
    override suspend fun loadList(): List<Mart> {
        val list = loadListWithKeyword("")
        if (list.isNotEmpty()) return list
        return fetchData()
    }

    private suspend fun fetchData(): List<Mart> {
        val list = service.getMartList().data
        localMartDao.clearALl()
        localMartDao.insertAll(list)
        return localMartDao.getAllMart()
    }

    override suspend fun loadListWithKeyword(keyword: String): List<Mart> {
        return localMartDao.searchMart("%${keyword}%")
    }


}