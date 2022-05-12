package com.jatri.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jatri.domain.entity.RentalSearchLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RentalSearchLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchLocation(rentalSearchLocationEntity: RentalSearchLocationEntity)

    @Query("SELECT * FROM rental_search_location ORDER BY created_at DESC")
    fun fetchSearchLocationList(): Flow<List<RentalSearchLocationEntity>>

    @Query("SELECT COUNT(name) FROM rental_search_location")
    fun fetchRentalSearchLocationItemCount(): Flow<Int>

    @Query("DELETE FROM rental_search_location WHERE created_at = (SELECT MIN(created_at) FROM rental_search_location)")
    suspend fun deleteRentalSearchLocationLastTwoItem()
}