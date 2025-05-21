package com.irfan.dtonton.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCardPModel(
    val id: Int?,
    val posterPath: String?,
) : Parcelable
