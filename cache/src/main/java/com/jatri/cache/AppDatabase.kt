package com.jatri.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jatri.cache.dao.RentalSearchLocationDao
import com.jatri.domain.entity.RentalSearchLocationEntity

@Database(entities = [RentalSearchLocationEntity::class], version = 10, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rentalSearchLocationDao(): RentalSearchLocationDao
}