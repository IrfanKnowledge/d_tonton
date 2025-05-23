package com.irfan.core.data.utils.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.irfan.core.data.model.WatchlistEntity

@Database(entities = [WatchlistEntity::class], version = 2, exportSchema = false)
@TypeConverters(WatchlistTypeEnumConverter::class)
abstract class DTontonDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao
}