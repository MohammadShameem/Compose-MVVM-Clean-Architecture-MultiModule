package com.jatri.domain.usecase.credential

import androidx.lifecycle.LiveData
import com.jatri.domain.base.AbsentLiveData
import com.jatri.domain.base.ApiUseCase
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse
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