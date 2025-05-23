package com.irfan.dtonton.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieCardUiModel(
    val id: Int?,
    val posterPath: String?,
) : Parcelable
