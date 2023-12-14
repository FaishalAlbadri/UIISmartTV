package com.faishalbadri.uiismarttv.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.News

class NewsViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _news = MutableLiveData<List<News>>()
    val newsData: LiveData<List<News>> = _news

    fun getNews() {
        _isLoading.value = true
        _news.value = DummyData().dataNews
        _isLoading.value = false
    }
}