package com.example.domain.usecase.login

import androidx.lifecycle.LiveData
import com.example.domain.base.AbsentLiveData
import com.example.domain.base.ApiUseCase
import com.example.domain.repository.OfflineCounterRepository
import com.example.entity.login.LoginEntity
import com.example.entity.res.ApiResponse
import javax.inject.Inject

class LoginApiUseCase @Inject constructor(
    private val offlineCounterRepository: OfflineCounterRepository
) : ApiUseCase<LoginApiUseCase.Params, LoginEntity> {

    data class Params(
        val phoneNumber: String,
        val password: String
    )

    override fun execute(params: Params?): LiveData<ApiResponse<LoginEntity>> {
        return params?.let { offlineCounterRepository.fetchLogin(it) }?: AbsentLiveData.create()
    }

}