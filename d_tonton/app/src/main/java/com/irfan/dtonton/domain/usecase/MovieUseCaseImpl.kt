package com.irfan.dtonton.domain.usecase

import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) :
    MovieUseCase {
    override fun getListMovieNowPlaying(): Flow<ResultState<List<MovieEntity>>> =
        movieRepository.getListMovieNowPlaying()

    override fun getListMoviePopular(): Flow<ResultState<List<MovieEntity>>> =
        movieRepository.getListMoviePopular()

    override fun getListMovieTopRated(): Flow<ResultState<List<MovieEntity>>> =
        movieRepository.getListMovieTopRated()

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailEntity>> =
        movieRepository.getMovieDetail(id)

    override fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<MovieEntity>>> =
        movieRepository.getMovieDetailListRecommendation(id)

    override fun getWatchlistMovie(): Flow<ResultState<List<MovieEntity>>> =
        movieRepository.getWatchlistMovie()

    override suspend fun insertWatchlistMovie(movie: MovieEntity): ResultState<Boolean> =
        movieRepository.insertWatchlistMovie(movie)

    override suspend fun deleteWatchlistMovie(id: Int): ResultState<Boolean> =
        movieRepository.deleteWatchlistMovie(id)

    override suspend fun isWatchlistMovie(id: Int): ResultState<Boolean> =
        movieRepository.isWatchlistMovie(id)
}