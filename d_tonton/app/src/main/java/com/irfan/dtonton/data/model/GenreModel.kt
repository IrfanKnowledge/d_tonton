package com.irfan.dtonton.data.model

import com.google.gson.annotations.SerializedName

data class GenreModel(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,
)
