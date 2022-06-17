package com.ohyeah5566.senaohw

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MartViewModel(
    private val repository: MartRepository
) : ViewModel() {
    private val _liveData: MutableLiveData<List<Mart>> = MutableLiveData()
    val liveData: LiveData<List<Mart>> = _liveData

    fun loadOne() {
        viewModelScope.launch {
            _liveData.value = repository.loadList()
        }
    }
}

class MartViewModelProvider(
    private val repository: MartRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MartViewModel(repository) as T
    }
}