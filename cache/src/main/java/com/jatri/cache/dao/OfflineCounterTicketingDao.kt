package com.jatri.cache.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jatri.domain.entity.BusCounterEntity
import com.jatri.domain.entity.SoldTicketEntity
import com.jatri.domain.entity.SoldTicketGroupWiseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OfflineCounterTicketingDao {

    @Insert
    suspend fun insertSelectedBusCounterEntity(entity:List<BusCounterEntity>)

    @Query("SELECT * FROM bus_counter")
    fun fetchSelectedBusCounterEntityList(): Flow<List<BusCounterEntity>>

    @Query("DELETE FROM bus_counter")
    suspend fun deleteSelectedBusCounterEntity()

    @Insert
    suspend fun insertSoldTicketEntity(entity: SoldTicketEntity)

    @Query("SELECT sum(fare) as total_fare, count(*) as ticket_count, name, fare FROM sold_ticket GROUP BY name, fare")
    fun fetchSoldTicketGroupWiseLiveData(): LiveData<List<SoldTicketGroupWiseEntity>>

    @Query("SELECT sum(fare) as total_fare, count(*) as ticket_count, name, fare FROM sold_ticket GROUP BY name, fare")
    suspend fun fetchSoldTicketGroupWise():List<SoldTicketGroupWiseEntity>

    @Query("SELECT IFNULL((SELECT COUNT(id) FROM sold_ticket),0)")
    fun fetchSoldTicketCountLiveData(): LiveData<Int>

    @Query("SELECT IFNULL((SELECT SUM (fare) FROM sold_ticket),0)")
    fun fetchSoldTicketTotalFareLiveData(): LiveData<Int>

    @Query("DELETE FROM sold_ticket")
    suspend fun deleteAllSoldTicket()
}