package com.irfan.dtonton.utils

import com.irfan.core.domain.entity.movie.Movie
import com.irfan.core.domain.entity.movie.MovieDetail
import com.irfan.dtonton.presentation.model.MovieCardUiModel
import com.irfan.dtonton.presentation.model.MovieDetailUiModel

object DataMapperHelper {
    fun mapListMovieEntityToListMovieCardUiModel(input: List<Movie>): List<MovieCardUiModel> {
        return input.map {
            MovieCardUiModel(
                id = it.id,
                posterPath = it.posterPath,
            )
        }
    }

    fun mapMovieDetailEntityToMovieDetailUiModel(input: MovieDetail): MovieDetailUiModel {
        return MovieDetailUiModel(
            id = input.id,
            posterPath = input.posterPath,
            title = input.title,
            listGenre = input.genres?.map {
                it.name
            },
            runtime = input.runtime,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            overview = input.overview,
        )
    }

    fun mapMovieDetailUiModelToMovieEntity(input: MovieDetailUiModel): Movie {
        return Movie(
            adult = null,
            backdropPath = input.posterPath,
            genreIds = null,
            id = input.id,
            originalTitle = null,
            overview = input.overview,
            popularity = null,
            posterPath = input.posterPath,
            releaseDate = null,
            title = input.title,
            video = null,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
        )
    }
}