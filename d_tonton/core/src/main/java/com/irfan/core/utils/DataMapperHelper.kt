package com.irfan.core.utils

import com.irfan.core.data.model.WatchlistEntity
import com.irfan.core.data.model.movie.MovieDetailResponse
import com.irfan.core.data.model.movie.MovieResponse
import com.irfan.core.domain.entity.movie.MovieDetail
import com.irfan.core.domain.entity.movie.Movie

object DataMapperHelper {
    fun mapListMovieModelToListMovieEntity(input: List<MovieResponse>): List<Movie> {
        return input.map {
            Movie(
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

    fun mapMovieDetailModelToMovieDetailEntity(input: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            adult = input.adult,
            backdropPath = input.backdropPath,
            genres = input.genres?.map {
                com.irfan.core.domain.entity.Genre(
                    id = it.id,
                    name = it.name,
                )
            },
            id = input.id,
            originalTitle = input.originalTitle,
            overview = input.overview,
            popularity = input.popularity,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            title = input.title,
            video = input.video,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
        )
    }

    fun mapListWatchlistTableToListMovieEntity(input: List<WatchlistEntity>): List<Movie> {
        return input.map {
            Movie(
                adult = null,
                backdropPath = null,
                genreIds = null,
                id = it.id,
                originalTitle = null,
                overview = it.overview,
                popularity = null,
                posterPath = it.posterPath,
                releaseDate = null,
                title = it.title,
                video = null,
                voteAverage = null,
                voteCount = null,
            )
        }
    }

    fun mapMovieEntityToWatchlistTable(
        input: Movie,
        type: WatchlistTypeEnum,
    ): WatchlistEntity {
        return WatchlistEntity(
            id = input.id ?: 0,
            title = input.title,
            posterPath = input.posterPath,
            overview = input.overview,
            watchlistType = type,
        )
    }
}