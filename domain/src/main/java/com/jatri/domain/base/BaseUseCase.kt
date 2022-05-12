package com.jatri.domain.base

import androidx.lifecycle.LiveData
import com.jatri.entity.res.ApiResponse

interface BaseUseCase
interface ApiUseCase<Params, Type> : BaseUseCase {
    fun execute(params: Params? = null): LiveData<ApiResponse<Type>>
}