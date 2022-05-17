package com.jatri.api.service.offlinecounterticketing

import com.jatri.apiresponse.login.LoginApiResponse
import com.jatri.apiresponse.offlinecounterticketing.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface OfflineCounterTicketingApiService {

    @GET("/user-api/v3/profile")
    fun fetchUserProfile(
        @Query("device_type") deviceType: String,
        @Query("device_id") device_id: String?,
        @Query("device_token") device_token: String?
    ): Single<UserProfileApiResponse>

    @POST("/offline-counter-api/v1/login")
    @FormUrlEncoded
    fun fetchLogin(@Field("mobile")mobile:String,@Field("password")password:String):Single<LoginApiResponse>


}