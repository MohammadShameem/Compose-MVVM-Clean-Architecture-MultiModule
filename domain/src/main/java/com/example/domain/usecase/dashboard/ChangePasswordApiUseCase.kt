package com.example.domain.usecase.dashboard

import androidx.lifecycle.LiveData
import com.example.domain.base.AbsentLiveData
import com.example.domain.base.ApiUseCase
import com.example.domain.repository.OfflineCounterRepository
import com.example.entity.dashboard.ChangePasswordApiEntity
import com.example.entity.res.ApiResponse
import javax.inject.Inject

class ChangePasswordApiUseCase @Inject constructor(
    private val offlineCounterRepository: OfflineCounterRepository
) : ApiUseCase<ChangePasswordApiUseCase.Params, ChangePasswordApiEntity> {

    data class Params(
        val oldPassword: String,
        val newPassword: String
    )

    override fun execute(params: Params?): LiveData<ApiResponse<ChangePasswordApiEntity>> {
        return params?.let { offlineCounterRepository.changePassword(it) }?: AbsentLiveData.create()
    }

}