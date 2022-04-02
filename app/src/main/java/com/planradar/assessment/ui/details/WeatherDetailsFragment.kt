package com.planradar.assessment.ui.details

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.planradar.assessment.R
import com.planradar.assessment.data.api.ApiHelper
import com.planradar.assessment.data.local.DbDatabase
import com.planradar.assessment.data.model.City
import com.planradar.assessment.data.model.WeatherData
import com.planradar.assessment.ui.viewModel.ViewModel
import com.planradar.assessment.ui.viewModel.ViewModelProviderFactory
import com.planradar.assessment.utils.Constants.Companion.ICON_URL
import com.planradar.assessment.utils.Constants.Companion.WEATHER_ICON_SIZE_FORMAT
import com.planradar.assessment.utils.Resource
import com.planradar.assessment.utils.checkNetwork.checkConnection
import com.planradar.assessment.utils.views.progressLoading
import kotlinx.android.synthetic.main.fragment_weather_details.*
import kotlin.math.roundToInt

class WeatherDetailsFragment : Fragment(R.layout.fragment_weather_details) {
    lateinit var navController: NavController
    lateinit var viewModel: ViewModel
    private val args: WeatherDetailsFragmentArgs by navArgs()

    companion object {
        @JvmStatic
        fun show(navController: NavController, city: City) {
            val action =
                WeatherDetailsFragmentDirections.actionNavigateToWeatherDetailsFragment(city)
            navController.navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setClickListeners()
        obtainViewModel()
        subscribeToUI()
        if (checkConnection.getInstance().checkNetWork(context as Activity?))
            viewModel.getWeather(args.city.name!!)
         else
            Toast.makeText(context, getString(R.string.connect_internet), Toast.LENGTH_SHORT).show()

    }

    private fun obtainViewModel() {
        val viewModelProviderFactory =
            ViewModelProviderFactory(ApiHelper(DbDatabase(requireContext())))
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ViewModel::class.java)
    }

    private fun subscribeToUI() {


        viewModel.getWeatherLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        feedDataToViews(newsResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

    }

    private fun setClickListeners() {
        ivBack.setOnClickListener { navController.popBackStack() }
        ivDeleteCity.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun hideProgressBar() {
        progressLoading.HideProgress()
    }

    private fun showProgressBar() {
        progressLoading.CreateProgress(context)
    }


    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.delete_city))
        builder.setMessage(getString(R.string.delete_city_dialog_description, args.city))
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            viewModel.removeCity(args.city)
            navController.popBackStack()
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    @SuppressLint("StringFormatMatches")
    private fun feedDataToViews(data: WeatherData?) {
        val weather = data!!.weather[0]

        tvCityName.text = args.city.toString()
        tvDescription.text = weather.description
        tvTemperature.text = getString(
            R.string.temperature_value,
            fromKelvinToCelsius(data.main.temp!!)
        ) // Convert it to celsius
        tvWindspeed.text = getString(R.string.windspeed_value, data.wind.speed.toString())
        tvLocationDate.text =
            getString(R.string.weather_location_date, data.cityName, data.dateTime)

        (data.main.humidity.toString() + "%").also { tvHumidity.text = it }


        Glide.with(requireContext())
            .load("${ICON_URL}${weather.icon}${WEATHER_ICON_SIZE_FORMAT}")
            .into(ivWeatherIcon)
    }

    private fun fromKelvinToCelsius(k: Double): Double {
        return ((k - 273.15) * 100).roundToInt() / 100.0
    }

}