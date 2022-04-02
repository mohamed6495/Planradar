package com.planradar.assessment.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.planradar.assessment.data.api.ApiHelper
import com.planradar.assessment.data.model.City
import com.planradar.assessment.data.model.ErrorMessage
import com.planradar.assessment.data.model.WeatherData
import com.planradar.assessment.utils.Constants.Companion.DATE_TIME_FORMAT
import com.planradar.assessment.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ViewModel(
    val apiHelper: ApiHelper) : ViewModel() {
    val getWeatherLiveData: MutableLiveData<Resource<WeatherData>> = MutableLiveData()
    val getCityLiveData: MutableLiveData<Resource<City>> = MutableLiveData()


    fun getWeather(cityName:String) = viewModelScope.launch {
        getWeatherLiveData.postValue(Resource.Loading())
        try {
            val response = apiHelper.getWeatherData(cityName)
            getWeatherLiveData.postValue(handleWeather(response))
        } catch (e: Exception) {

        }
    }

    private fun handleWeather(
        response: Response<WeatherData>): Resource<WeatherData> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT)
                val currentDate = dateFormat.format(Date())
                resultResponse.dateTime = currentDate
                val city = City(resultResponse.cityId, resultResponse.cityName, resultResponse.sys.countryCode)
                saveCity(city)
                saveWeather(resultResponse)
               Log.d("TAG", "handleAuthenticate: "+resultResponse.cityId)
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(getErrorMessageFromGenericResponse(response.errorBody()!!)!!)
     //   return Resource.Error(response.message())
    }

    private fun getErrorMessageFromGenericResponse(httpException: ResponseBody): String? {
        var errorMessage: String? = null
        try {
            val body = httpException
            val adapter = Gson().getAdapter(ErrorMessage::class.java)
            val errorParser = adapter.fromJson(body?.string())
            errorMessage = errorParser.message
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            return errorMessage
        }
    }

    fun saveWeather(weatherData: WeatherData) = viewModelScope.launch {
        apiHelper.insertWeather(weatherData)
    }
    fun getSavedWeather(cityName: String):LiveData<List<WeatherData>> = apiHelper.getSavedWeather(cityName)


    fun saveCity(city: City) = viewModelScope.launch {
        apiHelper.insertCity(city)
    }

    fun getAllCities():  LiveData<List<City>> = apiHelper.getAllCities()
    fun removeCity(city: City) = viewModelScope.launch {
        apiHelper.removeCity(city)
    }




}