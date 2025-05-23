package com.irfan.core.domain.usecase

import com.irfan.core.utils.ResultState
import com.irfan.core.domain.entity.movie.MovieDetail
import com.irfan.core.domain.entity.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getListMovieNowPlaying(): Flow<ResultState<List<Movie>>>
    fun getListMoviePopular(): Flow<ResultState<List<Movie>>>
    fun getListMovieTopRated(): Flow<ResultState<List<Movie>>>
    fun getMovieDetail(id: Int): Flow<ResultState<MovieDetail>>
    fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<Movie>>>
    fun getWatchlistMovie(): Flow<ResultState<List<Movie>>>
    suspend fun insertWatchlistMovie(movie: Movie): ResultState<Boolean>
    suspend fun deleteWatchlistMovie(id: Int): ResultState<Boolean>
    suspend fun isWatchlistMovie(id: Int): ResultState<Boolean>
}