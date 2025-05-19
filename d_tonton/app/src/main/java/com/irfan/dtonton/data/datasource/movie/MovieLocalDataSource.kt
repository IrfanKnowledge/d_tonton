package com.irfan.dtonton.data.datasource.movie

import com.irfan.dtonton.data.model.WatchlistTable
import com.irfan.dtonton.data.model.WatchlistTypeEnum
import com.irfan.dtonton.data.utils.local.WatchlistDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface MovieLocalDataSource {
    fun getWatchlistMovie(): Flow<List<WatchlistTable>>
    suspend fun insertWatchlist(watchlist: WatchlistTable): Long
    suspend fun deleteWatchlist(id: Int): Int
    suspend fun isWatchlistExist(id: Int): Boolean
}

@Singleton
class MovieLocalDataSourceImpl @Inject constructor(private val watchlistDao: WatchlistDao) :
    MovieLocalDataSource {
    override fun getWatchlistMovie(): Flow<List<WatchlistTable>> =
        watchlistDao.getWatchlistByType(WatchlistTypeEnum.MOVIE)

    override suspend fun insertWatchlist(watchlist: WatchlistTable): Long =
        watchlistDao.insertWatchlist(watchlist)

    override suspend fun deleteWatchlist(id: Int): Int =
        watchlistDao.deleteWatchlist(id)

    override suspend fun isWatchlistExist(id: Int): Boolean =
        watchlistDao.isWatchlistExist(id)
}