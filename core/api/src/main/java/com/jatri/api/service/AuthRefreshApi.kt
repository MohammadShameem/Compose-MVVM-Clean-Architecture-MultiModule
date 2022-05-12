package com.jatri.api.service

import com.jatri.apiresponse.offlinecounterticketing.RefreshTokenApiResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthRefreshApi {
    @POST("/user-api/v3/refresh-token")
    @FormUrlEncoded
    fun refreshToken(
        @Field("last_token") lastToken: String
    ): Call<RefreshTokenApiResponse>
}
