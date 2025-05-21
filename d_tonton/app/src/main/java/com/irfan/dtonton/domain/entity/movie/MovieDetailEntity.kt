package com.irfan.dtonton.domain.entity.movie

import android.os.Parcelable
import com.irfan.dtonton.domain.entity.GenreEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val genres: List<GenreEntity>?,
    val id: Int?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
) : Parcelable
