package com.jatri.domain.repository

import androidx.lifecycle.LiveData
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse

interface OfflineCounterRepository {
    fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>>
}