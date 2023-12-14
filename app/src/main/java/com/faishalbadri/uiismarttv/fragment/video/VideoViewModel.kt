package com.faishalbadri.uiismarttv.fragment.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.Video

class VideoViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _video = MutableLiveData<List<Video>>()
    val videoData: LiveData<List<Video>> = _video

    fun getVideo() {
        _isLoading.value = true
        _video.value = DummyData().dataVideo
        _isLoading.value = false
    }
}