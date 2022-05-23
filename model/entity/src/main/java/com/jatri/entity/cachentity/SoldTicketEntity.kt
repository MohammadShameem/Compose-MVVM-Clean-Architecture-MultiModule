package com.jatri.entity.cachentity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jatri.common.dateparser.DateTimeFormat
import com.jatri.common.dateparser.DateTimeParser

@Entity(tableName = "sold_ticket")
data class SoldTicketEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    val serial:Int,
    val name:String,
    val fare:Int,
    val created_at:String = DateTimeParser.getCurrentDeviceDateTime(DateTimeFormat.sqlYMDHMS)
)