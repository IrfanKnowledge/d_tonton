package com.irfan.dtonton.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailPModel(
    val id: Int?,
    val posterPath: String?,
    val title: String?,
    val listGenre: List<String>?,
    val runtime: Int?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val overview: String?,
) : Parcelable
