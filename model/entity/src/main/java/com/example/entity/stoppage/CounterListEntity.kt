package com.example.entity.stoppage

import com.example.entity.cachentity.StoppageEntity

data class CounterListEntity(
    val counter_list: List<CounterEntity>
)

data class CounterEntity(
    val counter_name: String,
    val stoppage_list: List<StoppageEntity>
)
