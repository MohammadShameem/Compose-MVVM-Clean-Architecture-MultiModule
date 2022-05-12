package com.jatri.data.repoimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.jatri.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.jatri.apiresponse.offlinecounterticketing.UserProfileApiResponse
import com.jatri.data.mapper.credential.UserProfileApiMapper
import com.jatri.data.mapper.mapFromApiResponse
import com.jatri.data.wrapper.NetworkBoundResource
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.entity.res.ApiResponse
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OfflineCounterRepoImpl @Inject constructor(
    private val credentialApi: OfflineCounterTicketingApiService,
    private val userProfileApiMapper: UserProfileApiMapper,
    private val sharedPrefHelper: SharedPrefHelper,
    private val cacheProfile: UserProfileApiMapper.CacheProfile,

    ) : OfflineCounterRepository {

    override fun fetchUserProfile(): LiveData<ApiResponse<UserProfileApiEntity>> {
        return Transformations.map(object : NetworkBoundResource<UserProfileApiResponse>() {
            override fun createCall(): Single<UserProfileApiResponse> {
                return credentialApi.fetchUserProfile(
                    "android",
                    sharedPrefHelper.getString(SpKey.androidDeviceId),
                    sharedPrefHelper.getString(SpKey.firebaseToken)
                )
            }

        }.asLiveData().map { mapFromApiResponse(it, userProfileApiMapper) }) {
            if (it is ApiResponse.Success) {
                cacheProfile.cacheProfileData(it.data)
            }
            it
        }
    }




}