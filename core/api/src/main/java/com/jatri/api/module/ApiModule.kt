package com.jatri.api.module

import com.jatri.api.authrefresh.AuthRefreshServiceHolder
import com.jatri.api.service.AuthRefreshApi
import com.jatri.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.jatri.di.qualifier.AppBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideCredentialApi(@AppBaseUrl retrofit: Retrofit,authRefreshServiceHolder: AuthRefreshServiceHolder): OfflineCounterTicketingApiService {
        authRefreshServiceHolder.setAuthRefreshApi(retrofit.create(AuthRefreshApi::class.java))
        return retrofit.create(OfflineCounterTicketingApiService::class.java)
    }

}