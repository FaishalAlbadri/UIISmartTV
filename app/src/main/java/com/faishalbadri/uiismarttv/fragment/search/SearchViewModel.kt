package com.faishalbadri.uiismarttv.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faishalbadri.uiismarttv.api.APIService
import com.faishalbadri.uiismarttv.data.local.HomeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    sealed class State {
        object Loading : State()
        data class SuccessLoading(val data: List<HomeData>) : State()
        data class FailedLoading(val error: Exception) : State()
    }

    fun getSearch(search: String) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading)
        try {
            val content = APIService.getSearch(search)
            _state.postValue(State.SuccessLoading(content))
        } catch (e: Exception) {
            _state.postValue(State.FailedLoading(e))
        }
    }
}