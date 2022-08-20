package com.example.api.module
import com.example.api.service.offlinecounterticketing.OfflineCounterTicketingApiService
import com.example.di.qualifier.AppBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideOfflineTicketApiService(@AppBaseUrl retrofit: Retrofit) : OfflineCounterTicketingApiService{
        return retrofit.create(OfflineCounterTicketingApiService::class.java)
    }

}