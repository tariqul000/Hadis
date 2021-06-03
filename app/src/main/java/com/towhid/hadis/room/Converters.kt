package com.towhid.hadis.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.towhid.hadis.network.model.response.hadis_book.Collection

class Converters {

    @TypeConverter
    fun listToJson(value: List<Collection>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Collection>::class.java).toList()
}

