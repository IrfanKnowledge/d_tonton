package com.irfan.favorite.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatchlistCardListUiModel(
    val id: Int?,
    val posterPath: String?,
    val title: String?,
    val overview: String?,
) : Parcelable
