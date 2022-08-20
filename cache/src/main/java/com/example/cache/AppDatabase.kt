package com.example.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cache.dao.OfflineCounterTicketingDao
import com.example.entity.cachentity.StoppageEntity
import com.example.entity.cachentity.SoldTicketEntity

@Database(entities = [StoppageEntity::class,
                     SoldTicketEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun offlineCounterTicketingDao(): OfflineCounterTicketingDao
}