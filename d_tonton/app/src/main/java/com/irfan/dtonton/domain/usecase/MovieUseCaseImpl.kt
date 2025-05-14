package com.irfan.dtonton.domain.usecase

import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) : MovieUseCase {
    override fun getListMovieNowPlaying(): Flow<ResultState<List<MovieEntity>>> =
        movieRepository.getListMovieNowPlaying()

    override fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailEntity>> =
        movieRepository.getMovieDetail(id)
}