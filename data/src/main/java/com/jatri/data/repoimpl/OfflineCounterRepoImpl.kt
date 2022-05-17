package com.jatri.data.repoimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.jatri.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.jatri.apiresponse.dashboard.ChangePasswordProfileInfoApiResponse
import com.jatri.apiresponse.dashboard.SyncSoldTicketApiResponse
import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.data.mapper.dashboard.ChangePasswordProfileInfoMapper
import com.jatri.data.mapper.dashboard.SyncSoldTicketApiMapper
import com.jatri.data.mapper.login.LoginMapper
import com.jatri.data.mapper.mapFromApiResponse
import com.jatri.data.wrapper.NetworkBoundResource
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.domain.usecase.dashboard.ChangePasswordProfileInfoApiUseCase
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.dashboard.ChangePasswordProfileInfoApiEntity
import com.jatri.entity.dashboard.SyncSoldTicketBody
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OfflineCounterRepoImpl @Inject constructor(
    private val offlineCounterApi: OfflineCounterTicketingApiService,
    private val loginApiMapper: LoginMapper,
    private val changePasswordProfileInfoMapper: ChangePasswordProfileInfoMapper,
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
        params: ChangePasswordProfileInfoApiUseCase.Params
    ): LiveData<ApiResponse<ChangePasswordProfileInfoApiEntity>> {
        return object : NetworkBoundResource<ChangePasswordProfileInfoApiResponse>() {
            override fun createCall(): Single<ChangePasswordProfileInfoApiResponse> {
                return offlineCounterApi.changePassword(
                    params.oldPassword,
                    params.newPassword
                )
            }
        }.asLiveData().map { mapFromApiResponse(it,changePasswordProfileInfoMapper) }

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