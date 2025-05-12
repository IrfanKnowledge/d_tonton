package com.irfan.dtonton.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieResponseModel(
    @field:SerializedName("page")
    val page: Int?,

    @field:SerializedName("results")
    val results: List<MovieModel>?,

    @field:SerializedName("total_pages")
    val totalPages: Int?,

    @field:SerializedName("total_results")
    val totalResults: Int?,
)
