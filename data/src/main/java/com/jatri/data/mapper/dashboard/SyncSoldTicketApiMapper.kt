package com.jatri.data.mapper.dashboard

import com.jatri.apiresponse.dashboard.SyncSoldTicketApiResponse
import com.jatri.data.mapper.Mapper
import com.jatri.entity.dashboard.SyncedSoldTicketApiEntity
import javax.inject.Inject

class SyncSoldTicketApiMapper @Inject constructor() :
Mapper<SyncSoldTicketApiResponse, SyncedSoldTicketApiEntity>{
    override fun mapFromApiResponse(type: SyncSoldTicketApiResponse): SyncedSoldTicketApiEntity {
        return SyncedSoldTicketApiEntity(
            message = type.message
        )
    }

}