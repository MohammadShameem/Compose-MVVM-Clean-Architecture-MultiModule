package com.jatri.offlinecounterticketing.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.jatri.api.authrefresh.AuthRefreshServiceHolder
import com.jatri.cache.AppDatabase
import com.jatri.cache.dao.RentalSearchLocationDao
import com.jatri.sharedpref.SharedPrefHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun sharePrefHelper(@ApplicationContext context: Context): SharedPrefHelper =
        SharedPrefHelper(context)

    @Provides
    @Singleton
    fun provideAuthRefreshServiceHolder() : AuthRefreshServiceHolder =
        AuthRefreshServiceHolder()

    @Provides
    fun provideDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, "jatri_user.db").fallbackToDestructiveMigration().build()

    @Provides
    fun provideRentalSearchLocationDao(appDatabase: AppDatabase):
            RentalSearchLocationDao = appDatabase.rentalSearchLocationDao()



}