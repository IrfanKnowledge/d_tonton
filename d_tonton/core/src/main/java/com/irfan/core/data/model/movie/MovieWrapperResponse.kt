package com.irfan.core.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieWrapperResponse(
    @field:SerializedName("page")
    val page: Int?,

    @field:SerializedName("results")
    val results: List<MovieResponse>?,

    @field:SerializedName("total_pages")
    val totalPages: Int?,

    @field:SerializedName("total_results")
    val totalResults: Int?,
)
