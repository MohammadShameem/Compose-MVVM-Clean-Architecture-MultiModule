package com.jatri.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rental_search_location")
data class RentalSearchLocationEntity(
    @PrimaryKey(autoGenerate = false) val name:String,
    val lat:Double,
    val lng:Double,
    val created_at:Long = Calendar.getInstance().time.time
)