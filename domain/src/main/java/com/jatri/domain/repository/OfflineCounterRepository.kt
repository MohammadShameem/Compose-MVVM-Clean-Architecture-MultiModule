package com.jatri.domain.repository

import androidx.lifecycle.LiveData
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.entity.res.ApiResponse

interface OfflineCounterRepository {
    fun fetchUserProfile():LiveData<ApiResponse<UserProfileApiEntity>>
}