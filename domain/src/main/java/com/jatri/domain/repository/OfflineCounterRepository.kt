package com.jatri.domain.repository

import androidx.lifecycle.LiveData
import com.jatri.domain.usecase.credential.LoginApiUseCase
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse

interface OfflineCounterRepository {
    fun fetchUserProfile():LiveData<ApiResponse<UserProfileApiEntity>>
    fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>>
}