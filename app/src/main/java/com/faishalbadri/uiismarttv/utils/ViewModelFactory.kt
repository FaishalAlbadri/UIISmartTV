package com.faishalbadri.uiismarttv.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.faishalbadri.uiismarttv.di.ViewModelInjection
import com.faishalbadri.uiismarttv.fragment.home.HomeViewModel
import com.faishalbadri.uiismarttv.fragment.location.LocationViewModel

class ViewModelFactory(private val locationPreferences: LocationPreferences) : NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LocationViewModel::class.java) -> {
                LocationViewModel(locationPreferences) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(locationPreferences) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(ViewModelInjection.provideRepository(context))
            }.also { instance = it }
        }
    }
}