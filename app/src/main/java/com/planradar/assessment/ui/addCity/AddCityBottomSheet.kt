package com.planradar.assessment.ui.addCity

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.planradar.assessment.R
import com.planradar.assessment.data.api.ApiHelper
import com.planradar.assessment.data.local.DbDatabase
import com.planradar.assessment.ui.viewModel.ViewModel
import com.planradar.assessment.ui.viewModel.ViewModelProviderFactory
import com.planradar.assessment.utils.Resource
import com.planradar.assessment.utils.checkNetwork.checkConnection
import com.planradar.assessment.utils.views.progressLoading
import kotlinx.android.synthetic.main.sheet_add_city.*


class AddCityBottomSheet : BottomSheetDialogFragment() {
    lateinit var viewModel: ViewModel
    companion object {
        @JvmStatic
        fun show(navController: NavController) =
            navController.navigate(R.id.action_navigate_to_addCityFragment)
    }

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView: View = inflater.inflate(R.layout.sheet_add_city, container, false)
        obtainViewModel()
        subscribeToUI()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAddCityEditText()
    }

private fun setupAddCityEditText() {
        etAddNewCity.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
                if (etAddNewCity.text.toString().isEmpty())
                    Toast.makeText(context, getString(R.string.please_add_city), Toast.LENGTH_SHORT).show()
                else
                    checkAddCity(etAddNewCity.text.toString())
            }
            true
        }
        imgAddNewCity.setOnClickListener {
            if (etAddNewCity.text.toString().isEmpty())
                Toast.makeText(context, getString(R.string.please_add_city), Toast.LENGTH_SHORT).show()
            else
                checkAddCity(etAddNewCity.text.toString())
        }

    }
private fun checkAddCity(cityName: String) {
        if (checkConnection.getInstance().checkNetWork(context as Activity?))
            viewModel.getWeather(cityName)
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
                    dismiss()
                }
            }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let { message ->
                    dismiss()
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
            is Resource.Loading -> {
                showProgressBar()
            }
        }
    })


}

private fun hideProgressBar() {
    progressLoading.HideProgress()
}

private fun showProgressBar() {
    progressLoading.CreateProgress(context)
}

override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogStyle)
    val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    bottomSheetDialog.setOnShowListener { dialogInterface: DialogInterface ->
        val dialog = dialogInterface as BottomSheetDialog
        val bottomSheet =
            dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet).isHideable = true
        }
    }
    return bottomSheetDialog
}

}