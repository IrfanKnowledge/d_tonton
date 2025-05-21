package com.irfan.favorite.common

import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.favorite.presentation.model.WatchlistCardListPModel

object DataMapperHelper {
    fun mapListMovieEntityToListWatchlistCardListPModel(input: List<MovieEntity>): List<WatchlistCardListPModel> {
        return input.map {
            WatchlistCardListPModel(
                id = it.id,
                posterPath = it.posterPath,
                title = it.title,
                overview = it.overview,
            )
        }
    }
}