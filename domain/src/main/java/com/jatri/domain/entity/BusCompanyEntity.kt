package com.jatri.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class BusCompanyEntity(
    val counter_list:List<BusCompanyCounter>

)

data class BusCompanyCounter(
    val counter_name:String,
    val stoppage_list:List<BusCounterEntity>
)

@Entity(tableName = "bus_counter")
data class BusCounterEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    @ColumnInfo(name = "stoppage_name")val name:String,
    val fare:Int,
    val fare_student:Int,
)
