package com.faishalbadri.uiismarttv.fragment.radio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.RadioData

class RadioViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _radioData = MutableLiveData<List<RadioData>>()
    val radioData: LiveData<List<RadioData>> = _radioData

    fun getRadio() {
        _isLoading.value = true
        _radioData.value = DummyData().dataRadio
        _isLoading.value = false
    }

}