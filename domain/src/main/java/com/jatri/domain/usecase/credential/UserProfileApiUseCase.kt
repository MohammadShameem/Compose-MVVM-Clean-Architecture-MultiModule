package com.jatri.domain.usecase.credential

import androidx.lifecycle.LiveData
import com.jatri.domain.base.ApiUseCase
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.entity.res.ApiResponse
import javax.inject.Inject

class UserProfileApiUseCase @Inject constructor(
    private val offlineCounterRepository: OfflineCounterRepository
) : ApiUseCase<Unit, UserProfileApiEntity> {

    override fun execute(params: Unit?): LiveData<ApiResponse<UserProfileApiEntity>> {
        return offlineCounterRepository.fetchUserProfile()
    }
}