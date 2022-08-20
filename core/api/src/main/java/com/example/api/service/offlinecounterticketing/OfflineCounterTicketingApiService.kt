package com.example.api.service.offlinecounterticketing

import com.example.apiresponse.dashboard.ChangePasswordApiResponse
import com.example.apiresponse.dashboard.SyncSoldTicketApiResponse
import com.example.apiresponse.login.LoginApiResponse
import com.example.entity.dashboard.SyncSoldTicketBody
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