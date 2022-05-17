package com.jatri.domain.usecase.dashboard

import androidx.lifecycle.LiveData
import com.jatri.domain.base.AbsentLiveData
import com.jatri.domain.base.ApiUseCase
import com.jatri.domain.repository.OfflineCounterRepository
import com.jatri.entity.dashboard.SyncSoldTicketBody
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import com.jatri.entity.res.ApiResponse
import javax.inject.Inject

class SyncedSoldTicketApiUseCase @Inject constructor(
    private val offlineCounterRepository: OfflineCounterRepository
) : ApiUseCase<SyncSoldTicketBody, SyncedSoldTicketApiEntity> {

    override fun execute(params: SyncSoldTicketBody?): LiveData<ApiResponse<SyncedSoldTicketApiEntity>> {
        return params?.let { offlineCounterRepository.syncSoldTicketData(it) }?: AbsentLiveData.create()
    }
}