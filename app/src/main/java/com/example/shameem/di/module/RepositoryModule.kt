package com.example.shameem.di.module

import com.example.data.repoimpl.OfflineCounterRepoImpl
import com.example.domain.repository.OfflineCounterRepository
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