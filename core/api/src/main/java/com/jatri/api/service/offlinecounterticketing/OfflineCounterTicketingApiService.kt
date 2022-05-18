package com.jatri.api.service.offlinecounterticketing

import com.jatri.apiresponse.dashboard.ChangePasswordApiResponse
import com.jatri.apiresponse.dashboard.SyncSoldTicketApiResponse
import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.entity.dashboard.SyncSoldTicketBody
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface OfflineCounterTicketingApiService {

    @POST("/offline-counter-api/v1/login")
    @FormUrlEncoded
    fun fetchLogin(
        @Field("mobile")mobile:String,
        @Field("password")password:String
    ):Single<LoginApiResponse>

    @POST("/offline-counter-api/v1/change-password")
    @FormUrlEncoded
    fun changePassword(
        @Field("old_password") oldPassword:String,
        @Field("new_password") newPassword:String
    ): Single<ChangePasswordApiResponse>

    @POST("/offline-counter-api/v1/sync-ticket-data")
    fun syncSoldTicketData(@Body entity: SyncSoldTicketBody):Single<SyncSoldTicketApiResponse>


}