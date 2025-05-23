package com.irfan.core.data.utils.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.irfan.core.utils.WatchlistTypeEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist WHERE watchlist_type = :type")
    fun getWatchlistByType(type: WatchlistTypeEnum): Flow<List<com.irfan.core.data.model.WatchlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlist(watchlist: com.irfan.core.data.model.WatchlistEntity): Long

    @Query("DELETE FROM watchlist WHERE id = :id")
    suspend fun deleteWatchlist(id: Int): Int

    @Query("SELECT EXISTS(SELECT * FROM watchlist WHERE id = :id)")
    suspend fun isWatchlistExist(id: Int): Boolean
}