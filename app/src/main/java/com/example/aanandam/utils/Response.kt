package com.example.aanandam.utils

sealed class Response<T>(val data : T? = null, val errorMsg : String? = null){

    class Success<T>(data: T, errorMsg: String? = null) : Response<T>(data, errorMsg)
    class Error<T>(errorMsg: String, data : T? = null) : Response<T>(data, errorMsg)
    class Loading<T>: Response<T>()
}
