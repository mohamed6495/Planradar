package com.planradar.assessment.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
@Keep
data class WeatherData(
  @PrimaryKey(autoGenerate = true) val weatherId: Int,
  @SerializedName("main") val main: Main,
  @SerializedName("name") val cityName: String,
  @SerializedName("id") val cityId: Int,
  @SerializedName("weather") val weather: List<Weather>,
  @SerializedName("wind") val wind: Wind,
  @SerializedName("sys") val sys: Sys,
  @SerializedName("dateTime") var dateTime: String
) : Parcelable {

  @Parcelize
  data class Main(
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("pressure") val pressure: Int?,
    @SerializedName("temp") val temp: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    @SerializedName("temp_min") val tempMin: Double?
  ) : Parcelable

  @Parcelize
  data class Weather(
    @SerializedName("description") val description: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("main") val main: String?
  ) : Parcelable

  @Parcelize
  data class Wind(
    @SerializedName("deg") val deg: Int?,
    @SerializedName("speed") val speed: Double?
  ) : Parcelable

  @Parcelize
  data class Sys(
    @SerializedName("country") val countryCode: String?,
  ) : Parcelable
}