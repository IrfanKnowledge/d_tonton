package com.irfan.dtonton.data.utils.local

import androidx.room.TypeConverter
import com.irfan.core.common.WatchlistTypeEnum

class WatchlistTypeEnumConverter {
    @TypeConverter
    fun fromWatchlistTypeEnum(value: WatchlistTypeEnum): String {
        return value.name
    }

    @TypeConverter
    fun toWatchlistTypeEnum(value: String): WatchlistTypeEnum {
        return try {
            WatchlistTypeEnum.valueOf(value)
        } catch (e: IllegalArgumentException) {
            WatchlistTypeEnum.UNKNOWN
        }
    }
}