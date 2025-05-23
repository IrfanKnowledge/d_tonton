package com.irfan.favorite.utils

import com.irfan.core.domain.entity.movie.Movie
import com.irfan.favorite.presentation.model.WatchlistCardListUiModel

object DataMapperHelper {
    fun mapListMovieEntityToListWatchlistCardListUiModel(input: List<Movie>): List<WatchlistCardListUiModel> {
        return input.map {
            WatchlistCardListUiModel(
                id = it.id,
                posterPath = it.posterPath,
                title = it.title,
                overview = it.overview,
            )
        }
    }
}