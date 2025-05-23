package com.irfan.core.data.datasource.movie

import com.irfan.core.data.model.WatchlistEntity
import com.irfan.core.data.utils.local.WatchlistDao
import com.irfan.core.utils.WatchlistTypeEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface MovieLocalDataSource {
    fun getWatchlistMovie(): Flow<List<WatchlistEntity>>
    suspend fun insertWatchlist(watchlist: WatchlistEntity): Long
    suspend fun deleteWatchlist(id: Int): Int
    suspend fun isWatchlistExist(id: Int): Boolean
}

@Singleton
class MovieLocalDataSourceImpl @Inject constructor(private val watchlistDao: WatchlistDao) :
    MovieLocalDataSource {
    override fun getWatchlistMovie(): Flow<List<WatchlistEntity>> =
        watchlistDao.getWatchlistByType(WatchlistTypeEnum.MOVIE)

    override suspend fun insertWatchlist(watchlist: WatchlistEntity): Long =
        watchlistDao.insertWatchlist(watchlist)

    override suspend fun deleteWatchlist(id: Int): Int =
        watchlistDao.deleteWatchlist(id)

    override suspend fun isWatchlistExist(id: Int): Boolean =
        watchlistDao.isWatchlistExist(id)
}