package com.faishalbadri.uiismarttv.fragment.radio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.local.RadioData

class RadioViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _radioData = MutableLiveData<MutableList<RadioData>>()
    val radioData: LiveData<MutableList<RadioData>> = _radioData

    private var data: MutableList<RadioData> = ArrayList()

    fun getRadio() {
        _isLoading.value = true
        data.clear()
        data.addAll(dataRadio)
        _isLoading.value = false
        _radioData.value = data
    }

    val dataRadio: List<RadioData> = listOf(
        RadioData(
            "Unisi Jogja",
            "104.5 FM",
            "https://pbs.twimg.com/profile_images/1212065846845067265/nAMpYJkH_400x400.jpg",
            "https://studio1.indostreamers.com:8002/stream/1/"
        ),
        RadioData(
            "Prambors FM Jogja",
            "95.8 FM",
            "https://www.gudeg.net/cni-content/uploads/modules/direktori/logo/20150730014924.jpg",
            "https://23743.live.streamtheworld.com/PRAMBORS_FM_SC?dist=onlineradiobox"
        ),
        RadioData(
            "Voks Radio Jogja",
            "105.3 FM",
            "https://yt3.googleusercontent.com/NGVvzlBxwQqVHCXQgfLe9Rwv9h51TxIRFU4lNlojkYb6xesY_H27fN0OEEnp8qKGkXC7iWwX0g=s900-c-k-c0x00ffffff-no-rj",
            "https://svara-stream.radioddns.net:8443/yogyakarta_voksradio"
        ),
        RadioData(
            "RRI Pro 3 Jogja",
            "00.0 FM",
            "https://static.mytuner.mobi/media/tvos_radios/NCUHGk4XRJ.png",
            "https://stream-node0.rri.co.id/streaming/14/9014/kbrn.mp3"
        ),
        RadioData(
            "iRadio Jogja",
            "88.7 FM",
            "https://static.mytuner.mobi/media/tvos_radios/XjUArs9BMg.jpg",
            "https://n09.radiojar.com/4ywdgup3bnzuv?rj-ttl=5&rj-tok=AAABjA2Fx8UAvH_ezF_FOzX7Qw"
        ),
        RadioData(
            "Unisia Jogja",
            "00.0 FM",
            "https://radioindostream.my.id/wp-content/uploads/2021/04/Radio-Unisia-AM-Yogyakarta-600x401.jpg",
            "http://202.162.36.222:8000/;"
        )
    )

}