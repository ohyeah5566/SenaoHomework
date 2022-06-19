package com.ohyeah5566.senaohw

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch

class MartViewModel(
    private val repository: MartRepository
) : ViewModel() {
    var key = ""

    @OptIn(ExperimentalPagingApi::class)
    val flow = Pager(
        PagingConfig(20),
        remoteMediator = MartRemoteMediator(repository),
    ) {
        repository.searchMartPagingSource(key)
    }.flow.cachedIn(viewModelScope)

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }

    private val _martDetail = MutableLiveData<Mart>()
    val martDetail: LiveData<Mart> = _martDetail

    fun setMartDetail(mart: Mart) {
        _martDetail.value = mart
    }
}

class MartViewModelProvider(
    private val repository: MartRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MartViewModel(repository) as T
    }
}