package com.planradar.assessment.data.api

import com.planradar.assessment.data.local.DbDatabase
import com.planradar.assessment.data.model.City
import com.planradar.assessment.data.model.WeatherData


class ApiHelper( val db: DbDatabase) {

    suspend fun getWeatherData(cityName: String) =
        RetrofitInstance.api.getWeatherData(cityName)


    ////////////WeatherDB
    suspend fun insertWeather(weather: WeatherData) = db.weathersDao().insert(weather)
            fun getSavedWeather(cityName: String) = db.weathersDao().getWeatherHistory(cityName)

    ////////////cityDB
    suspend fun insertCity(city: City) = db.citiesDao().insert(city)
    fun getAllCities() = db.citiesDao().getAllCities()
    suspend fun removeCity(city: City) = db.citiesDao().delete(city)


}