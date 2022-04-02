package com.planradar.assessment.ui.showCity


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.planradar.assessment.R
import com.planradar.assessment.data.api.ApiHelper
import com.planradar.assessment.data.local.DbDatabase
import com.planradar.assessment.ui.addCity.AddCityBottomSheet
import com.planradar.assessment.ui.details.WeatherDetailsFragment
import com.planradar.assessment.ui.history.WeatherHistoryFragment
import com.planradar.assessment.ui.viewModel.ViewModel
import com.planradar.assessment.ui.viewModel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_home.*


class cityFragment : Fragment(R.layout.fragment_home) {
    lateinit var db: DbDatabase
    lateinit var navController: NavController
    lateinit var viewModel: ViewModel
    lateinit var cityAdapter: cityAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initViews()
        obtainViewModel()
        subscribeToUI()
    }

    fun initViews() {
        db = DbDatabase(requireContext())

        cityAdapter = cityAdapter()
        cityAdapter.setOnItemClickListener {
            WeatherDetailsFragment.show(navController, it)
        }
        cityAdapter.setOnItemClickListenerToHistory {
            WeatherHistoryFragment.show(navController, it)
        }


        fabAddCity.setOnClickListener {
            AddCityBottomSheet.show(navController)
        }
    }

    fun obtainViewModel() {
        val viewModelProviderFactory =
            ViewModelProviderFactory(ApiHelper(DbDatabase(requireContext())))
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(ViewModel::class.java)
    }

    fun subscribeToUI() {
        viewModel.getAllCities().observe(viewLifecycleOwner, Observer { response ->
            response.let {
                cityAdapter.differ.submitList(it)
                rvCities.apply {
                    adapter = cityAdapter
                    layoutManager = LinearLayoutManager(activity)
                }

            }
        })


    }

}