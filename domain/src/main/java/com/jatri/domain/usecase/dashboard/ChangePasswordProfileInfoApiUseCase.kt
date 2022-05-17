package com.jatri.domain.usecase.dashboard

import androidx.lifecycle.LiveData
import com.jatri.domain.base.AbsentLiveData
import com.jatri.domain.base.ApiUseCase
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.entity.dashboard.ChangePasswordProfileInfoApiEntity
import com.jatri.entity.res.ApiResponse
import javax.inject.Inject

class ChangePasswordProfileInfoApiUseCase @Inject constructor(
    private val offlineCounterRepository: OfflineCounterRepository
) : ApiUseCase<ChangePasswordProfileInfoApiUseCase.Params, ChangePasswordProfileInfoApiEntity> {

    data class Params(
        val oldPassword: String,
        val newPassword: String
    )

    override fun execute(params: Params?): LiveData<ApiResponse<ChangePasswordProfileInfoApiEntity>> {
        return params?.let { offlineCounterRepository.changePassword(it) }?: AbsentLiveData.create()
    }

}