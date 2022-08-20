package com.example.entity.cachentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey




@Entity(tableName = "bus_counter")
data class StoppageEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    @ColumnInfo(name = "stoppage_name")val name:String,
    val fare:Int,
    val fare_student:Int,
)
