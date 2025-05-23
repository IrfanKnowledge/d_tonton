package com.irfan.core.domain.entity.movie

import android.os.Parcelable
import com.irfan.core.domain.entity.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    val adult: Boolean?,
    val backdropPath: String?,
    val genres: List<Genre>?,
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
