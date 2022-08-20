package com.example.entity.res

sealed class ApiResponse<T>{

    data class Progress<T>(var loading: Boolean) : ApiResponse<T>()
    data class Success<T>(var data: T) : ApiResponse<T>()
    data class Failure<T>(val message:String,val code:Int) : ApiResponse<T>()
    companion object {
        fun <T> loading(isLoading: Boolean): ApiResponse<T> = Progress(isLoading)
        fun <T> success(data: T): ApiResponse<T> = Success(data)
        fun <T> failure(message:String="",code:Int=0): ApiResponse<T> = Failure(message,code)
    }
}