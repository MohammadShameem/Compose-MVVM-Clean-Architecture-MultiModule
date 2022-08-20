package com.example.data.repoimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.example.apiresponse.dashboard.ChangePasswordApiResponse
import com.example.apiresponse.dashboard.SyncSoldTicketApiResponse
import com.example.apiresponse.login.LoginApiResponse
import com.example.data.mapper.dashboard.ChangePasswordMapper
import com.example.data.mapper.dashboard.SyncSoldTicketApiMapper
import com.example.data.mapper.login.LoginMapper
import com.example.data.mapper.mapFromApiResponse
import com.example.data.wrapper.NetworkBoundResource
import com.example.domain.repository.OfflineCounterRepository
import com.example.domain.usecase.dashboard.ChangePasswordApiUseCase
import com.example.domain.usecase.login.LoginApiUseCase
import com.example.entity.dashboard.ChangePasswordApiEntity
import com.example.entity.dashboard.SyncSoldTicketBody
import com.example.entity.dashboard.SyncedSoldTicketApiEntity
import com.example.entity.login.LoginEntity
import com.example.entity.res.ApiResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OfflineCounterRepoImpl @Inject constructor(
    private val offlineCounterApi: OfflineCounterTicketingApiService,
    private val loginApiMapper: LoginMapper,
    private val changePasswordMapper: ChangePasswordMapper,
    private val syncSoldTicketApiMapper: SyncSoldTicketApiMapper
    ) : OfflineCounterRepository {

    override fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>> {
        return object : NetworkBoundResource<LoginApiResponse>() {
            override fun createCall(): Single<LoginApiResponse> {
                return offlineCounterApi.fetchLogin(
                    params.phoneNumber,
                    params.password
                )
            }
        }.asLiveData().map { mapFromApiResponse(it,loginApiMapper) }
    }

    override fun changePassword(
        params: ChangePasswordApiUseCase.Params
    ): LiveData<ApiResponse<ChangePasswordApiEntity>> {
        return object : NetworkBoundResource<ChangePasswordApiResponse>() {
            override fun createCall(): Single<ChangePasswordApiResponse> {
                return offlineCounterApi.changePassword(
                    params.oldPassword,
                    params.newPassword
                )
            }
        }.asLiveData().map { mapFromApiResponse(it,changePasswordMapper) }

    }

    override fun syncSoldTicketData(
        syncSoldTicketBody: SyncSoldTicketBody
    ): LiveData<ApiResponse<SyncedSoldTicketApiEntity>> {
        return object : NetworkBoundResource<SyncSoldTicketApiResponse>() {
            override fun createCall(): Single<SyncSoldTicketApiResponse> {
                return offlineCounterApi.syncSoldTicketData(syncSoldTicketBody)
            }
        }.asLiveData().map { mapFromApiResponse(it,syncSoldTicketApiMapper) }
    }
}