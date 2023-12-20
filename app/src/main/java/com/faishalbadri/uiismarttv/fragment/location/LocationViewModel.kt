package com.faishalbadri.uiismarttv.fragment.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.APIService
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.data.local.LocationDataPreferences
import com.faishalbadri.uiismarttv.utils.LocationPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(private val locationPreferences: LocationPreferences) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        data class SuccessLoadLocation(val data: List<Adzan>) : State()
        data class FailedLoadLocation(val error: Exception) : State()
    }

    fun getLocation(provinsi: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            if (provinsi.equals("0")) {
                _state.postValue(State.SuccessLoadLocation(APIService.getLocationProvinsi()))
            } else {
                _state.postValue(State.SuccessLoadLocation(APIService.getLocationKota(provinsi)))
            }
        } catch (e: Exception) {
            _state.postValue(State.FailedLoadLocation(e))
        }
    }

    fun getLocation(): LiveData<LocationDataPreferences> {
        return locationPreferences.getLocation().asLiveData()
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        locationPreferences.logout()
    }

    fun saveSession(locationDataPreferences: LocationDataPreferences) =
        viewModelScope.launch(Dispatchers.IO) {
            locationPreferences.saveLocation(locationDataPreferences)

        }
}