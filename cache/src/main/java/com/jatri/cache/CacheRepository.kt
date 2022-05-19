package com.jatri.cache

import androidx.lifecycle.LiveData
import com.jatri.cache.dao.OfflineCounterTicketingDao
import com.jatri.domain.entity.StoppageEntity
import com.jatri.domain.entity.SoldTicketEntity
import com.jatri.domain.entity.SoldTicketGroupWiseEntity
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

    fun fetchSoldTicketGroupWiseLiveData():LiveData<List<SoldTicketGroupWiseEntity>> =
        cacheDao.fetchSoldTicketGroupWiseLiveData()

    suspend fun fetchSoldTicketGroupWise():List<SoldTicketGroupWiseEntity> =
        cacheDao.fetchSoldTicketGroupWise()

    fun fetchSoldTicketCount(): Flow<Int> =
        cacheDao.fetchSoldTicketCount()

    fun fetchSoldTicketTotalFare(): Flow<Int> =
        cacheDao.fetchSoldTicketTotalFare()

    suspend fun deleteAllSoldTicket() =
        cacheDao.deleteAllSoldTicket()
}