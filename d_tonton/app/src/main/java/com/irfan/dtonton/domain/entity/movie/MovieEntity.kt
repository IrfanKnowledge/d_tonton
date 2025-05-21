package com.irfan.dtonton.domain.entity.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieEntity(
    val adult: Boolean?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val id: Int?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
) : Parcelable
