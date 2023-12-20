package com.faishalbadri.uiismarttv.fragment.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.APIService
import com.faishalbadri.uiismarttv.data.local.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        data class SuccessLoadVideo(val data: List<Video>) : State()
        data class FailedLoadVideo(val error: Exception) : State()
    }

    init {
        getVideo()
    }

    fun getVideo() = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            _state.postValue(State.SuccessLoadVideo(APIService.getVideo()))
        } catch (e: Exception) {
            _state.postValue(State.FailedLoadVideo(e))
        }
    }
}