package com.irfan.dtonton.data.utils.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.irfan.dtonton.data.model.WatchlistTable

@Database(entities = [WatchlistTable::class], version = 1, exportSchema = false)
@TypeConverters(WatchlistTypeEnumConverter::class)
abstract class DTontonDatabase : RoomDatabase() {

    abstract fun watchlistDao(): WatchlistDao
}