package com.ohyeah5566.senaohw

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test


internal class ExtensionLoadStateKtTest {

    @Test
    fun testCollectError() = runTest {
        val prepend = LoadState.NotLoading(true)
        val refresh = LoadState.NotLoading(true)
        val exception = Exception()
        val append = LoadState.Error(exception)
        val source = LoadStates(refresh, prepend, append)
        val mediator = LoadStates(refresh, prepend, append)
        val flow = listOf(
            CombinedLoadStates(refresh, prepend, append, source, mediator),
        ).asFlow()
        val actionOnError = mockk<(throwable: Throwable) -> Unit>(relaxed = true)

        flow.collectError(actionOnError)
        verify { actionOnError.invoke(exception) }
    }

    //測試連續相同的錯誤是否只會執行一次
    @Test
    fun testCollectError1() = runTest {
        val prepend = LoadState.NotLoading(true)
        val refresh = LoadState.NotLoading(true)
        val exception = Exception()
        val append = LoadState.Error(exception)
        val source = LoadStates(refresh, prepend, append)
        val mediator = LoadStates(refresh, prepend, append)
        val flow = listOf(
            CombinedLoadStates(refresh, prepend, append, source, mediator),
        ).asFlow()
        val actionOnError = mockk<(throwable: Throwable) -> Unit>(relaxed = true)

        flow.collectError(actionOnError)
        verify(exactly = 1) { actionOnError.invoke(exception) }
    }

    //測試是否有執行兩次不同的錯誤
    @Test
    fun testCollectError2() = runTest {
        val prepend = LoadState.NotLoading(true)
        val refresh = LoadState.NotLoading(true)
        val exception = Exception()
        val append = LoadState.Error(exception)
        val source = LoadStates(refresh, prepend, append)
        val mediator = LoadStates(refresh, prepend, append)
        val exception2 = Exception()
        val append2 = LoadState.Error(exception2)
        val mediator2 = LoadStates(refresh, prepend, append2)
        val flow = listOf(
            CombinedLoadStates(refresh, prepend, append, source, mediator),
            CombinedLoadStates(refresh, prepend, append, source, mediator),
            CombinedLoadStates(refresh, prepend, append, source, mediator2),
        ).asFlow()
        val actionOnError = mockk<(throwable: Throwable) -> Unit>(relaxed = true)

        flow.collectError(actionOnError)
        verifyOrder {
            actionOnError.invoke(exception)
            actionOnError.invoke(exception2)
        }
    }

}