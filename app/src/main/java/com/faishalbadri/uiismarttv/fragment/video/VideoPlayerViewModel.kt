package com.faishalbadri.uiismarttv.fragment.video

import android.content.Context
import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import com.maxrave.kotlinyoutubeextractor.YtFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoPlayerViewModel(
    id: String,
    context: Context
) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        data class SuccessLoadVideo(val link: String) : State()
        data class FailedLoadVideo(val error: String) : State()
    }

    companion object {
        const val HD_720 = 22
        const val SD_360 = 18
    }

    init {
        getLink(id, context)
    }

    fun getLink(id: String, context: Context) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            val yt = YTExtractor(con = context, CACHING = true, LOGGING = true, retryCount = 3)
            var ytFiles: SparseArray<YtFile>? = null
            yt.extract(id)
            if (yt.state == com.maxrave.kotlinyoutubeextractor.State.SUCCESS) {
                ytFiles = yt.getYTFiles()
                ytFiles.let {
                    val a = it?.get(SD_360).let { data ->
                        data?.url.toString()
                    }
                    _state.postValue(State.SuccessLoadVideo(a))
                }
            } else {
                _state.postValue(State.FailedLoadVideo("Url video not found"))
            }

        } catch (e: Exception) {
            _state.postValue(State.FailedLoadVideo(e.message.toString()))
        }
    }
}