package com.example.data.mapper

import com.example.entity.res.ApiResponse

interface Mapper<R,E>{
    fun mapFromApiResponse(type:R):E
}
fun<R,E> mapFromApiResponse(apiResponse: ApiResponse<R>, mapper: Mapper<R, E>):ApiResponse<E>{
    return when(apiResponse){
        is ApiResponse.Success-> ApiResponse.success(mapper.mapFromApiResponse(apiResponse.data))
        is ApiResponse.Progress->ApiResponse.loading(apiResponse.loading)
        is ApiResponse.Failure->ApiResponse.failure(apiResponse.message,apiResponse.code)
    }
}