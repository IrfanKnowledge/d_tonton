package com.irfan.dtonton.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_table")
data class WatchlistTable(
    @PrimaryKey
    val id: Int,

    val title: String?,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    val overview: String?,

    @ColumnInfo(name = "watchlist_type", defaultValue = "UNKNOWN")
    val watchlistType: WatchlistTypeEnum,
)
