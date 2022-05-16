package com.jatri.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.jatri.dateutil.DateTimeFormatter

@Entity(tableName = "sold_ticket")
data class SoldTicketEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    val serial:Int,
    val name:String,
    val fare:Int,
    val created_at:String = DateTimeFormatter.getCurrentDeviceDateTime(DateTimeFormatter.sqlYMDHMS)
)