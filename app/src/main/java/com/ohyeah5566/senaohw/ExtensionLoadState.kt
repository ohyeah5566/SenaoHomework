package com.ohyeah5566.senaohw

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter


suspend fun Flow<CombinedLoadStates>.collectError(
    actionOnError: (throwable: Throwable) -> Unit
) {
    distinctUntilChangedBy { //避免刷新時再次show出error
        (it.mediator?.append as? LoadState.Error)?.error
    }.filter {
        it.mediator?.append is LoadState.Error
    }.collect {
        val error = (it.mediator?.append as LoadState.Error).error
        actionOnError.invoke(error)
    }

}