package com.planradar.assessment.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.planradar.assessment.R
import com.planradar.assessment.data.api.ApiHelper
import com.planradar.assessment.data.local.DbDatabase
import com.planradar.assessment.ui.viewModel.ViewModel
import com.planradar.assessment.ui.viewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_weather_history.*

class WeatherHistoryFragment: Fragment(R.layout.fragment_weather_history) {
    private val args: WeatherHistoryFragmentArgs by navArgs()
    lateinit var navController: NavController
    lateinit var historyAdapter: historyAdapter
    lateinit var viewModel: ViewModel
    companion object {
        @JvmStatic
        fun show(navController: NavController, cityName: String?) {
            val action = WeatherHistoryFragmentDirections.actionNavigateToWeatherHistoryFragment(cityName)
            navController.navigate(action)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setupViews()
        setClickListeners()
        obtainViewModel()
        subscribeToUI()
    }

    fun obtainViewModel() {
        val viewModelProviderFactory =
            ViewModelProviderFactory(ApiHelper(DbDatabase(requireContext())))
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ViewModel::class.java)
    }
    fun subscribeToUI() {
        viewModel.getSavedWeather(args.cityName!!).observe(viewLifecycleOwner, Observer { response ->
            response.let {

                historyAdapter.differ.submitList(it)
                rvHistory.apply {
                    adapter = historyAdapter
                    layoutManager = LinearLayoutManager(activity)
                }

            }
        })
    }

    private fun setClickListeners() {
        ivBack.setOnClickListener { navController.popBackStack() }
    }

    private fun setupViews() {
        historyAdapter = historyAdapter()
        rvHistory.layoutManager = LinearLayoutManager(context)
      tvCityName.text = getString(R.string.city_name_history, args.cityName)

    }

}