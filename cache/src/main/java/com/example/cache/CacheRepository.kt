package com.example.cache

import com.example.cache.dao.OfflineCounterTicketingDao
import com.example.entity.cachentity.StoppageEntity
import com.example.entity.cachentity.SoldTicketEntity
import com.example.entity.cachentity.SoldTicketGroupWiseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheRepository @Inject constructor(
    private val cacheDao: OfflineCounterTicketingDao
) {

    suspend fun insertSelectedBusCounterEntity(entity:List<StoppageEntity>) =
        cacheDao.insertSelectedBusCounterEntity(entity)

    fun fetchSelectedBusCounterEntityList():Flow<List<StoppageEntity>>{
        return cacheDao.fetchSelectedBusCounterEntityList()
    }

    suspend fun deleteSelectedBusCounterEntity() =
        cacheDao.deleteSelectedBusCounterEntity()

    suspend fun insertSoldTicketEntity(entity: SoldTicketEntity) =
        cacheDao.insertSoldTicketEntity(entity)

    fun fetchSoldTicketGroupWiseDataFlow():Flow<List<SoldTicketGroupWiseEntity>> =
        cacheDao.fetchSoldTicketGroupWiseDataFlow()

    suspend fun fetchSoldTicketGroupWise():List<SoldTicketGroupWiseEntity> =
        cacheDao.fetchSoldTicketGroupWise()

    fun fetchSoldTicketCount(): Flow<Int> =
        cacheDao.fetchSoldTicketCount()

    fun fetchSoldTicketTotalFare(): Flow<Int> =
        cacheDao.fetchSoldTicketTotalFare()

    suspend fun deleteAllSoldTicket() =
        cacheDao.deleteAllSoldTicket()
}