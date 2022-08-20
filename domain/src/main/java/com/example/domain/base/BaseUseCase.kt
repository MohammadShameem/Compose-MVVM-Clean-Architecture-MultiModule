package com.example.domain.base

import androidx.lifecycle.LiveData
import com.example.entity.res.ApiResponse

interface BaseUseCase
interface ApiUseCase<Params, Type> : BaseUseCase {
    fun execute(params: Params? = null): LiveData<ApiResponse<Type>>
}