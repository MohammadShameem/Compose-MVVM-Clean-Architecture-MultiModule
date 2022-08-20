package com.example.data.mapper.dashboard

import com.example.apiresponse.dashboard.SyncSoldTicketApiResponse
import com.example.data.mapper.Mapper
import com.example.entity.dashboard.SyncedSoldTicketApiEntity
import javax.inject.Inject

class SyncSoldTicketApiMapper @Inject constructor() :
Mapper<SyncSoldTicketApiResponse, SyncedSoldTicketApiEntity>{
    override fun mapFromApiResponse(type: SyncSoldTicketApiResponse): SyncedSoldTicketApiEntity {
        return SyncedSoldTicketApiEntity(
            message = type.message
        )
    }

}