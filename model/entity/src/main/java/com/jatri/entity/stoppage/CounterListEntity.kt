package com.jatri.entity.stoppage

import com.jatri.entity.cachentity.StoppageEntity

data class CounterListEntity(
    val counter_list: List<CounterEntity>
)

data class CounterEntity(
    val counter_name: String,
    val stoppage_list: List<StoppageEntity>
)
