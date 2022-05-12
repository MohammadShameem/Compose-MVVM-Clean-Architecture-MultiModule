package com.jatri.api.authrefresh
import com.jatri.sharedpref.SharedPrefHelper
import com.jatri.sharedpref.SpKey
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthenticationRefreshToken @Inject constructor(
    private var sharedPrefHelper: SharedPrefHelper,
    private var authRefreshServiceHolder: AuthRefreshServiceHolder
) : Authenticator{
    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == 401){
            val authenticatorCall = authRefreshServiceHolder.getAuthRefreshApi()?.refreshToken(sharedPrefHelper.getString(SpKey.userAuthKey))
            val refreshTokenResponse = authenticatorCall?.execute()
            if (refreshTokenResponse?.body() != null && refreshTokenResponse.isSuccessful && refreshTokenResponse.code() == 200){
                refreshTokenResponse.body()?.let {
                    sharedPrefHelper.putString(SpKey.userAuthKey,it.token)
                    response.request.newBuilder().header("Authorization", it.token).build()
                }

            } else null
        } else null
    }

}