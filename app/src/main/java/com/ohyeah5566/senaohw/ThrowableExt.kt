package com.ohyeah5566.senaohw

import retrofit2.HttpException

fun Throwable.customMessage() : String {
    return if(this is HttpException){
        this.response()?.errorBody()?.string() ?: "oops"
    } else {
        message ?: "oops"
    }
}