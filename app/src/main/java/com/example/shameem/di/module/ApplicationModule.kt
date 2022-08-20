package com.example.shameem.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.cache.AppDatabase
import com.example.cache.dao.OfflineCounterTicketingDao
import com.example.sharedpref.SharedPrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun sharePrefHelper(@ApplicationContext context: Context): SharedPrefHelper =
        SharedPrefHelper(context)

    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "jatri_offline_counter.db")
            .fallbackToDestructiveMigration().build()

    @Provides
    fun provideOfflineCounterTicketingDao(appDatabase: AppDatabase):
            OfflineCounterTicketingDao = appDatabase.offlineCounterTicketingDao()





}