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
        data.add(RadioData("https://www.gudeg.net/cni-content/uploads/modules/direktori/logo/20150730014924.jpg","Prambors FM Jogja", "https://23743.live.streamtheworld.com/PRAMBORS_FM_SC?dist=onlineradiobox"))
        data.add(RadioData("https://static.mytuner.mobi/media/tvos_radios/NCUHGk4XRJ.png","RRI Pro 3 Jogja", "https://stream-node0.rri.co.id/streaming/14/9014/kbrn.mp3"))
        data.add(RadioData("https://static.mytuner.mobi/media/tvos_radios/XjUArs9BMg.jpg","iRadio Jogja", "https://n09.radiojar.com/4ywdgup3bnzuv?rj-ttl=5&rj-tok=AAABjA2Fx8UAvH_ezF_FOzX7Qw"))
        data.add(RadioData("https://radioindostream.my.id/wp-content/uploads/2021/04/Radio-Unisia-AM-Yogyakarta-600x401.jpg","Unisia Jogja", "http://202.162.36.222:8000/;"))
        data.add(RadioData("http://jogjastreamers.com/cni-content/uploads/modules/radio/20210531114736.png","Unisi Jogja", "https://studio1.indostreamers.com:8002/stream/1/"))
        _isLoading.value = false
        _radioData.value = data
    }

}