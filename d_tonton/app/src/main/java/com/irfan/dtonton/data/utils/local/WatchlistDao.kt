package com.irfan.dtonton.data.utils.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.irfan.core.common.WatchlistTypeEnum
import com.irfan.dtonton.data.model.WatchlistTable
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist_table")
    fun getAllWatchlist(): Flow<List<WatchlistTable>>

    @Query("SELECT * FROM watchlist_table WHERE watchlist_type = :type")
    fun getWatchlistByType(type: WatchlistTypeEnum): Flow<List<WatchlistTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(watchlist: WatchlistTable): Long

    @Query("DELETE FROM watchlist_table WHERE id = :id")
    suspend fun deleteWatchlist(id: Int): Int

    @Query("SELECT EXISTS(SELECT * FROM watchlist_table WHERE id = :id)")
    suspend fun isWatchlistExist(id: Int): Boolean
}