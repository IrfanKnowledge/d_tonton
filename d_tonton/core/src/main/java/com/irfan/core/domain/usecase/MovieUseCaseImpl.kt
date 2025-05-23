package com.irfan.core.domain.usecase

import com.irfan.core.utils.ResultState
import com.irfan.core.domain.entity.movie.MovieDetail
import com.irfan.core.domain.entity.movie.Movie
import com.irfan.core.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) :
    MovieUseCase {
    override fun getListMovieNowPlaying(): Flow<ResultState<List<Movie>>> =
        movieRepository.getListMovieNowPlaying()

    override fun getListMoviePopular(): Flow<ResultState<List<Movie>>> =
        movieRepository.getListMoviePopular()

    override fun getListMovieTopRated(): Flow<ResultState<List<Movie>>> =
        movieRepository.getListMovieTopRated()

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetail>> =
        movieRepository.getMovieDetail(id)

    override fun getMovieDetailListRecommendation(id: Int): Flow<ResultState<List<Movie>>> =
        movieRepository.getMovieDetailListRecommendation(id)

    override fun getWatchlistMovie(): Flow<ResultState<List<Movie>>> =
        movieRepository.getWatchlistMovie()

    override suspend fun insertWatchlistMovie(movie: Movie): ResultState<Boolean> =
        movieRepository.insertWatchlistMovie(movie)

    override suspend fun deleteWatchlistMovie(id: Int): ResultState<Boolean> =
        movieRepository.deleteWatchlistMovie(id)

    override suspend fun isWatchlistMovie(id: Int): ResultState<Boolean> =
        movieRepository.isWatchlistMovie(id)
}