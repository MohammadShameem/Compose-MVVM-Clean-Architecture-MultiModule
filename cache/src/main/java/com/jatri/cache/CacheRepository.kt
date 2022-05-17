package com.jatri.cache

import androidx.lifecycle.LiveData
import com.jatri.cache.dao.OfflineCounterTicketingDao
import com.jatri.domain.entity.BusCounterEntity
import com.jatri.domain.entity.SoldTicketEntity
import com.jatri.domain.entity.SoldTicketGroupWiseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheRepository @Inject constructor(
    private val cacheDao: OfflineCounterTicketingDao
) {

    suspend fun insertSelectedBusCounterEntity(entity:List<BusCounterEntity>) =
        cacheDao.insertSelectedBusCounterEntity(entity)

    fun fetchSelectedBusCounterEntityList():Flow<List<BusCounterEntity>>{
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

    fun fetchSoldTicketCountLiveData(): LiveData<Int> =
        cacheDao.fetchSoldTicketCountLiveData()

    fun fetchSoldTicketTotalFareLiveData(): LiveData<Int> =
        cacheDao.fetchSoldTicketTotalFareLiveData()

    suspend fun deleteAllSoldTicket() =
        cacheDao.deleteAllSoldTicket()
}