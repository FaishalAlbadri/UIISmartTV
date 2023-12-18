package com.faishalbadri.uiismarttv.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.uii.APIServiceUII
import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.RadioData
import com.faishalbadri.uiismarttv.data.dummy.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        data class SuccessLoading(val data: List<HomeData>) : State()
        data class FailedLoading(val error: Exception) : State()
    }

    init {
        getHome()
    }

    private fun getHome() = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)

        try {
            val content = APIServiceUII.getHome()

            _state.postValue(State.SuccessLoading(content))
        } catch (e: Exception) {
            _state.postValue(State.FailedLoading(e))
        }
    }

}