package com.planradar.assessment.data.local

import android.content.Context
import androidx.room.*
import com.planradar.assessment.data.model.City
import com.planradar.assessment.data.model.WeatherData


@Database(entities = [City::class, WeatherData::class], version = 1, exportSchema = false)
@TypeConverters(WeatherListConverter::class, MainConverter::class, SysConverter::class, WindConverter::class)
abstract class DbDatabase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao
    abstract fun weathersDao(): WeatherDao


    companion object {
        @Volatile
        private var instance: DbDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DbDatabase::class.java,
                "weather.db"
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}