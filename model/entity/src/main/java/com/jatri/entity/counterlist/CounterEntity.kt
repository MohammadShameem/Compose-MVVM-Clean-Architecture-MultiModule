package com.jatri.entity.counterlist

data class CounterEntity(
    val counter_name: String,
    val stoppage_list: List<StoppageEntity>
)