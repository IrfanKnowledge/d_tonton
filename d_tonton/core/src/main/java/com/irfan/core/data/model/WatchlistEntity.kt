package com.irfan.core.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.irfan.core.utils.WatchlistTypeEnum

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey
    val id: Int,

    val title: String?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    val overview: String?,

    @ColumnInfo(name = "watchlist_type", defaultValue = "UNKNOWN")
    val watchlistType: WatchlistTypeEnum,
)
