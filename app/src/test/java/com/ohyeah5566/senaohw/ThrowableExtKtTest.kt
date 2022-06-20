package com.ohyeah5566.senaohw

import io.mockk.every
import io.mockk.mockk
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Response.error
import retrofit2.Retrofit

class ThrowableExtKtTest {


    @Test
    fun testException() {
        val th = Throwable("A")
        assert(th.customMessage() == "A")
        val ex = Exception()
        assert(ex.customMessage() == "oops")

        val httpErrorMessage = "retrofit error message"
        val httpException = HttpException(
            Response.error<Any>(403, httpErrorMessage
                .toResponseBody("plain/text".toMediaTypeOrNull())
            )
        )
        assert(httpException.customMessage() == httpErrorMessage)

        val mockedHttpException = mockk<HttpException>(relaxed = true)
        every { mockedHttpException.response() } returns null
        assert(mockedHttpException.customMessage() == "oops")
    }
}