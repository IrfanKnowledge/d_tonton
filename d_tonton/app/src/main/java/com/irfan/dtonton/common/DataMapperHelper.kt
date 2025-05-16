package com.irfan.dtonton.common

import com.irfan.dtonton.data.model.movie.MovieDetailModel
import com.irfan.dtonton.data.model.movie.MovieModel
import com.irfan.dtonton.domain.entity.GenreEntity
import com.irfan.dtonton.domain.entity.movie.MovieDetailEntity
import com.irfan.dtonton.domain.entity.movie.MovieEntity
import com.irfan.dtonton.presentation.model.MovieCardPModel

object DataMapperHelper {
    fun mapListMovieModelToListMovieEntity(input: List<MovieModel>): List<MovieEntity> {
        return input.map {
            MovieEntity(
                adult = it.adult,
                backdropPath = it.backdropPath,
                genreIds = it.genreIds,
                id = it.id,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                video = it.video,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
            )
        }
    }

    fun mapListMovieEntityToListMovieCardPModel(input: List<MovieEntity>): List<MovieCardPModel> {
        return input.map {
            MovieCardPModel(
                id = it.id,
                posterPath = it.posterPath,
            )
        }
    }

    fun mapMovieDetailModelToMovieDetailEntity(input: MovieDetailModel): MovieDetailEntity {
        return MovieDetailEntity(
            adult = input.adult,
            backdropPath = input.backdropPath,
            genres = input.genres.map {
                GenreEntity(
                    id = it.id,
                    name = it.name,
                )
            },
            id = input.id,
            originalTitle = input.originalTitle,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            title = input.title,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
        )
    }
}