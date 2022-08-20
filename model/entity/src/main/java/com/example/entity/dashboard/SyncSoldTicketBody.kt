package com.example.entity.dashboard

data class SyncSoldTicketBody(
    val device_id: String,
    val ticket_collection: List<SyncSoldTicketBodyCollection>
)

data class  SyncSoldTicketBodyCollection(
    val stoppage: String,
    val total_fare: Int,
    val total_ticket: Int,
    val unit_fare: Int
)