package com.faishalbadri.uiismarttv.fragment.radio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.RadioData

class RadioViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _radioData = MutableLiveData<MutableList<RadioData>>()
    val radioData: LiveData<MutableList<RadioData>> = _radioData

    private  var data:MutableList<RadioData> = ArrayList()

    fun getRadio(){
        _isLoading.value = true
        data.add(RadioData("","Prambors FM Jogja", "https://23743.live.streamtheworld.com/PRAMBORS_FM_SC?dist=onlineradiobox"))

        _isLoading.value = false
        _radioData.value = data
    }

}