package com.planradar.assessment.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.planradar.assessment.data.api.ApiHelper

class ViewModelProviderFactory(val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModel(apiHelper) as T
    }

}