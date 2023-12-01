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
        data.add(RadioData("Unisi Jogja","104.5 FM","http://jogjastreamers.com/cni-content/uploads/modules/radio/20210531114736.png", "https://studio1.indostreamers.com:8002/stream/1/"))
        data.add(RadioData("Prambors FM Jogja", "95.8 FM","https://www.gudeg.net/cni-content/uploads/modules/direktori/logo/20150730014924.jpg", "https://23743.live.streamtheworld.com/PRAMBORS_FM_SC?dist=onlineradiobox"))
        data.add(RadioData("RRI Pro 3 Jogja", "00.0 FM","https://static.mytuner.mobi/media/tvos_radios/NCUHGk4XRJ.png", "https://stream-node0.rri.co.id/streaming/14/9014/kbrn.mp3"))
        data.add(RadioData("iRadio Jogja","88.7 FM","https://static.mytuner.mobi/media/tvos_radios/XjUArs9BMg.jpg", "https://n09.radiojar.com/4ywdgup3bnzuv?rj-ttl=5&rj-tok=AAABjA2Fx8UAvH_ezF_FOzX7Qw"))
        data.add(RadioData("Unisia Jogja","00.0 FM","https://radioindostream.my.id/wp-content/uploads/2021/04/Radio-Unisia-AM-Yogyakarta-600x401.jpg", "http://202.162.36.222:8000/;"))
        _isLoading.value = false
        _radioData.value = data
    }

}