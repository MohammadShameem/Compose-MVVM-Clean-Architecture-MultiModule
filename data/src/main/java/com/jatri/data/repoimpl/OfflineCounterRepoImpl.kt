package com.jatri.data.repoimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.jatri.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.apiresponse.offlinecounterticketing.UserProfileApiResponse
import com.jatri.data.mapper.credential.UserProfileApiMapper
import com.jatri.data.mapper.login.LoginMapper
import com.jatri.data.mapper.mapFromApiResponse
import com.jatri.data.wrapper.NetworkBoundResource
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.domain.usecase.credential.LoginApiUseCase
import com.jatri.entity.credential.UserProfileApiEntity
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OfflineCounterRepoImpl @Inject constructor(
    private val offlineCounterApi: OfflineCounterTicketingApiService,
    private val userProfileApiMapper: UserProfileApiMapper,
    private val loginApiMapper: LoginMapper,
    private val sharedPrefHelper: SharedPrefHelper,
    private val cacheProfile: UserProfileApiMapper.CacheProfile,

    ) : OfflineCounterRepository {

    override fun fetchUserProfile(): LiveData<ApiResponse<UserProfileApiEntity>> {
        return Transformations.map(object : NetworkBoundResource<UserProfileApiResponse>() {
            override fun createCall(): Single<UserProfileApiResponse> {
                return offlineCounterApi.fetchUserProfile(
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

    override fun fetchLogin(loginParams: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>> {
        return Transformations.map(object : NetworkBoundResource<LoginApiResponse>() {
            override fun createCall(): Single<LoginApiResponse> {
                return offlineCounterApi.fetchLogin(
                    loginParams.phoneNumber,
                    loginParams.password
                )
            }
        }.asLiveData().map { mapFromApiResponse(it,loginApiMapper) }){
               if(it is ApiResponse.Success){

               }
            it
        }
    }


}