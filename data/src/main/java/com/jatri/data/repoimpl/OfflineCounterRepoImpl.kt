package com.jatri.data.repoimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.jatri.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.data.mapper.login.LoginMapper
import com.jatri.data.mapper.mapFromApiResponse
import com.jatri.data.wrapper.NetworkBoundResource
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.domain.usecase.login.LoginApiUseCase
import com.jatri.entity.login.LoginEntity
import com.jatri.entity.res.ApiResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class OfflineCounterRepoImpl @Inject constructor(
    private val offlineCounterApi: OfflineCounterTicketingApiService,
    private val loginApiMapper: LoginMapper
    ) : OfflineCounterRepository {

    override fun fetchLogin(params: LoginApiUseCase.Params): LiveData<ApiResponse<LoginEntity>> {
        return Transformations.map(object : NetworkBoundResource<LoginApiResponse>() {
            override fun createCall(): Single<LoginApiResponse> {
                return offlineCounterApi.fetchLogin(
                    params.phoneNumber,
                    params.password
                )
            }
        }.asLiveData().map { mapFromApiResponse(it,loginApiMapper) }){
               if(it is ApiResponse.Success){

               }
            it
        }
    }


}