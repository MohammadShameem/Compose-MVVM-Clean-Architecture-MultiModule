package com.jatri.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jatri.cache.dao.OfflineCounterTicketingDao
import com.jatri.domain.entity.StoppageEntity
import com.jatri.domain.entity.SoldTicketEntity

@Database(entities = [StoppageEntity::class,
                     SoldTicketEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun offlineCounterTicketingDao(): OfflineCounterTicketingDao
}