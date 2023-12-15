package com.faishalbadri.uiismarttv.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.Video

class NewsViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _news = MutableLiveData<List<News>>()
    val newsData: LiveData<List<News>> = _news

    private val _newsDetail = MutableLiveData<News>()
    val newsDetailData: LiveData<News> = _newsDetail

    private var dataNews: MutableList<News> = ArrayList()

    fun getNews() {
        _isLoading.value = true
        _news.value = DummyData().dataNews
        _isLoading.value = false
    }

    fun getDetailNews(id: String) {
        _isLoading.value = true
        for (i in 0 until DummyData().dataNews.size) {
            val news = DummyData().dataNews.get(i)
            if (id == news.id) {
                _newsDetail.value = DummyData().dataNews.get(i)
            }
        }
        _isLoading.value = false
    }

    fun getNewsRecommendation() {
        _isLoading.value = true
        dataNews.clear()

        for (i in 0 until 5) {
            dataNews.add(DummyData().dataNews.get(i))
        }

        _news.value = dataNews

        _isLoading.value = false
    }
}