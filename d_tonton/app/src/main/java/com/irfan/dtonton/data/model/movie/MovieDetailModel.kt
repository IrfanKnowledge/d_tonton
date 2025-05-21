package com.irfan.dtonton.data.model.movie

import com.google.gson.annotations.SerializedName
import com.irfan.dtonton.data.model.GenreModel

data class MovieDetailModel(
    @field:SerializedName("adult")
    val adult: Boolean?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("genres")
    val genres: List<GenreModel>?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("original_title")
    val originalTitle: String?,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("popularity")
    val popularity: Double?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("runtime")
    val runtime: Int?,

    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("video")
    val video: Boolean?,

    @field:SerializedName("vote_average")
    val voteAverage: Double?,

    @field:SerializedName("vote_count")
    val voteCount: Int?
)