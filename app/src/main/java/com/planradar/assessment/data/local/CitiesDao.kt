package com.planradar.assessment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.planradar.assessment.data.model.City
import com.planradar.assessment.utils.room.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CitiesDao : BaseDao<City> {
  @Query("SELECT * FROM city")
  abstract fun getAllCities(): LiveData<List<City>>
}