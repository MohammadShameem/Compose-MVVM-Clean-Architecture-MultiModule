package com.example.domain.usecase.dashboard

import androidx.lifecycle.LiveData
import com.example.domain.base.AbsentLiveData
import com.example.domain.base.ApiUseCase
import com.example.domain.repository.OfflineCounterRepository
import com.example.entity.dashboard.SyncSoldTicketBody
import com.example.entity.dashboard.SyncedSoldTicketApiEntity
import com.example.entity.res.ApiResponse
import javax.inject.Inject

class SyncedSoldTicketApiUseCase @Inject constructor(
    private val offlineCounterRepository: OfflineCounterRepository
) : ApiUseCase<SyncSoldTicketBody, SyncedSoldTicketApiEntity> {

    override fun execute(params: SyncSoldTicketBody?): LiveData<ApiResponse<SyncedSoldTicketApiEntity>> {
        return params?.let { offlineCounterRepository.syncSoldTicketData(it) }?: AbsentLiveData.create()
    }
}