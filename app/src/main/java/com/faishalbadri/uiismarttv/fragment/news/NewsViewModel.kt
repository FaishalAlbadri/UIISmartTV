package com.faishalbadri.uiismarttv.fragment.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.APIService
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

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
            if (category.equals(HomeData.News)) {
                _state.postValue(State.SuccessLoadNews(APIService.getNews(), true))
            } else {
                _state.postValue(State.SuccessLoadNews(APIService.getPojokRektor(), true))
            }
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

    fun getDetailNews(id: String) {

    }

    fun getNewsRecommendation() {

    }
}