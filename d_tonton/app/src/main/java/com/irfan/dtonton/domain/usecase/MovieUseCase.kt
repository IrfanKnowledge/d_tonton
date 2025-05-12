package com.irfan.dtonton.domain.usecase

import com.irfan.core.common.ResultState
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getListMovieNowPlaying(): Flow<ResultState<List<MovieEntity>>>
    fun getMovieDetail(id: Int): Flow<ResultState<MovieDetailEntity>>
}