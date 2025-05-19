package com.irfan.dtonton.domain.repository

import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getListMovieNowPlaying(): Flow<ResultState<List<MovieEntity>>>
    fun getListMoviePopular(): Flow<ResultState<List<MovieEntity>>>
    fun getListMovieTopRated(): Flow<ResultState<List<MovieEntity>>>
    fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailEntity>>
    fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<MovieEntity>>>
    fun getWatchlistMovie(): Flow<ResultState<List<MovieEntity>>>
    suspend fun insertWatchlistMovie(movie: MovieEntity): ResultState<Boolean>
    suspend fun deleteWatchlistMovie(id: Int): ResultState<Boolean>
    suspend fun isWatchlistMovie(id: Int): ResultState<Boolean>
}