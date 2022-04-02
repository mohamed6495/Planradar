package com.planradar.assessment.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.planradar.assessment.data.model.WeatherData.Weather
import com.planradar.assessment.data.model.WeatherData.Wind
import com.planradar.assessment.data.model.WeatherData.Main
import com.planradar.assessment.data.model.WeatherData.Sys


class WeatherListConverter {
    private val type = object : TypeToken<List<Weather>>() {}.type

    @TypeConverter
    fun toJson(obj: List<Weather>): String? = Gson().toJson(obj, type)

    @TypeConverter
    fun toList(json: String?): List<Weather> = Gson().fromJson(json, type)
}

class WindConverter {
    private val type = object : TypeToken<Wind>() {}.type
    @TypeConverter
    fun toJson(data: Wind?): String {
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toObj(json: String): Wind? {
        return Gson().fromJson(json, type)
    }
}

class SysConverter {
    private val type = object : TypeToken<Sys>() {}.type
    @TypeConverter
    fun toJson(data: Sys?): String {
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toObj(json: String): Sys? {
        return Gson().fromJson(json, type)
    }
}

class MainConverter {
    private val type = object : TypeToken<Main>() {}.type
    @TypeConverter
    fun toJson(data: Main?): String {
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toObj(json: String): Main? {
        return Gson().fromJson(json, type)
    }
}
