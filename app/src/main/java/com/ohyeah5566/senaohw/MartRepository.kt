package com.ohyeah5566.senaohw

interface MartRepository {
    suspend fun loadList(): List<Mart>
}

class MartRepositoryRemote(
    private val service: MartService
) : MartRepository {
    override suspend fun loadList(): List<Mart> {
        return service.getMartList().data
    }
}