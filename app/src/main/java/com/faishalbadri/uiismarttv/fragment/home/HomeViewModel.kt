package com.faishalbadri.uiismarttv.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.RadioData
import com.faishalbadri.uiismarttv.data.dummy.Video

class HomeViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _contentData = MutableLiveData<List<HomeData>>()
    val contentData: LiveData<List<HomeData>> = _contentData

    private var dataVideo: MutableList<Video> = ArrayList()
    private var dataNews: MutableList<News> = ArrayList()

    fun getContent() {
        _isLoading.value = true
        dataNews.clear()
        dataVideo.clear()
        dataVideo.addAll(DummyData().dataVideo)
        dataNews.addAll(DummyData().dataNews)

        dataVideo.add(Video("0", "", "", "", ""))
        dataNews.add(News("0", "", "", ""))

        var dataResponse: List<HomeData> = listOf(
            HomeData(
                msg = HomeData.Banner,
                list = DummyData().dataBanner
            ),
            HomeData(
                msg = HomeData.Video,
                list = dataVideo
            ),
            HomeData(
                msg = HomeData.News,
                list = dataNews
            )
        )
        _contentData.value = dataResponse
        _isLoading.value = false
    }

}