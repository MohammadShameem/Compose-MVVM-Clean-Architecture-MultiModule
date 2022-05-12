package com.jatri.api.authrefresh

import com.jatri.api.service.AuthRefreshApi

class AuthRefreshServiceHolder{
    private var authRefreshApi: AuthRefreshApi? = null
    fun getAuthRefreshApi(): AuthRefreshApi? {
        return authRefreshApi
    }

    fun setAuthRefreshApi(authRefreshApi: AuthRefreshApi) {
        this.authRefreshApi = authRefreshApi
    }
}