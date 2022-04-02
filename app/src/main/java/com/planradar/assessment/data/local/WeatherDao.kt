package com.planradar.assessment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.planradar.assessment.data.model.WeatherData
import com.planradar.assessment.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDao : BaseDao<WeatherData> {
  @Query("SELECT * FROM weatherdata WHERE cityName IN(:cityName)")
  abstract fun getWeatherHistory(cityName: String): LiveData<List<WeatherData>>

}