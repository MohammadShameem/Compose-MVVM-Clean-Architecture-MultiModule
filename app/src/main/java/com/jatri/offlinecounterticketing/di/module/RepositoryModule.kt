package com.jatri.offlinecounterticketing.di.module

import com.jatri.data.repoimpl.OfflineCounterRepoImpl
import com.jatri.domain.repository.OfflineCounterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindOfflineRepository(offlineCounterRepoImpl: OfflineCounterRepoImpl): OfflineCounterRepository
}