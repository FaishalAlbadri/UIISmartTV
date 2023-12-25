package com.faishalbadri.uiismarttv.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.APIService
import com.faishalbadri.uiismarttv.data.local.HomeData
import com.faishalbadri.uiismarttv.data.local.LocationDataPreferences
import com.faishalbadri.uiismarttv.utils.LocationPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val locationPreferences: LocationPreferences) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        data class SuccessLoading(val data: List<HomeData>) : State()
        data class FailedLoading(val error: Exception) : State()
    }

    init {
        viewModelScope.launch {
            getLocation().collect(){
                getHome(it.id, it.nama)
            }
        }
    }

    fun getHome(id: String, nama: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            val content = APIService.getHome(id, nama)
            _state.postValue(State.SuccessLoading(content))
        } catch (e: Exception) {
            _state.postValue(State.FailedLoading(e))
        }
    }

    fun getLocation(): Flow<LocationDataPreferences> {
        return locationPreferences.getLocation()
    }

}