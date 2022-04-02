package com.planradar.assessment.data.api


import com.planradar.assessment.data.model.WeatherData
import com.planradar.assessment.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @Headers("Accept:application/json", "Content-Type:application/json")
    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("q") cityName: String? = null,
        @Query("appid") appKey: String = API_KEY
    ): Response<WeatherData>


}