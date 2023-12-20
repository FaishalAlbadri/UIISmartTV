package com.faishalbadri.uiismarttv.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.APIService
import com.faishalbadri.uiismarttv.data.local.HomeData
import com.faishalbadri.uiismarttv.data.local.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel() : ViewModel() {

    private var page = 1

    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        object LoadingMore : State()
        data class SuccessLoadNews(val data: List<News>, val hasMore: Boolean) : State()
        data class FailedLoadNews(val error: Exception) : State()
        data class SuccessLoadDetailNews(val data: News) : State()
        data class FailedLoadDetailNews(val error: Exception) : State()
    }

    fun getNews(category: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            page = 1
            val api: List<News>
            if (category.equals(HomeData.News)) {
                api = APIService.getNews()
            } else {
                api = APIService.getPojokRektor()
            }
            _state.postValue(State.SuccessLoadNews(api, true))
        } catch (e: Exception) {
            _state.postValue(State.FailedLoadNews(e))
        }
    }

    fun loadMoreNews(category: String) = viewModelScope.launch(Dispatchers.IO) {
        val currentState = state.value
        if (currentState is State.SuccessLoadNews) {
            _state.postValue(State.LoadingMore)
            try {
                if (category.equals(HomeData.News)) {
                    val news = APIService.getNews(page + 1)
                    page += 1
                    _state.postValue(
                        State.SuccessLoadNews(
                            data = currentState.data + news,
                            hasMore = news.isNotEmpty(),
                        )
                    )
                } else {
                    val pojokRektor = APIService.getPojokRektor(page + 1)
                    page += 1
                    _state.postValue(
                        State.SuccessLoadNews(
                            data = currentState.data + pojokRektor,
                            hasMore = pojokRektor.isNotEmpty(),
                        )
                    )
                }
            } catch (e: Exception) {
                _state.postValue(State.FailedLoadNews(e))
            }
        }
    }

    fun getDetailNews(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            _state.postValue(State.SuccessLoadDetailNews(APIService.getDetailNews(id)))
        } catch (e: Exception) {
            _state.postValue(State.FailedLoadDetailNews(e))
        }
    }
}