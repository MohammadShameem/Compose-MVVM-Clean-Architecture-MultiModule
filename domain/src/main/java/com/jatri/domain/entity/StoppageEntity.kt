package com.jatri.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class CounterListEntity(
    val counter_list: List<CounterEntity>
)

data class CounterEntity(
    val counter_name: String,
    val stoppage_list: List<StoppageEntity>
)


@Entity(tableName = "bus_counter")
data class StoppageEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    @ColumnInfo(name = "stoppage_name")val name:String,
    val fare:Int,
    val fare_student:Int,
)
