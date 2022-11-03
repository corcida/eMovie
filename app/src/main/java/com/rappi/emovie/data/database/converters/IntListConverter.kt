package com.rappi.emovie.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class IntListConverter {

    @TypeConverter
    fun stringToIntList(json: String?): List<Int>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.fromJson<List<Int>>(json, type)
    }

    @TypeConverter
    fun intListToString(list: List<Int?>?): String? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Int?>?>() {}.type
        return gson.toJson(list, type)
    }

}