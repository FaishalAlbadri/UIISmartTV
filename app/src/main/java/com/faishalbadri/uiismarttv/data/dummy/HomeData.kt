package com.faishalbadri.uiismarttv.data.dummy

import com.faishalbadri.uiismarttv.adapter.AppAdapter

class HomeData(

    val msg: String,
    val list: List<AppAdapter.Item>

) : AppAdapter.Item {

    var selectedIndex = 0

    override lateinit var itemType: AppAdapter.Type

    companion object {
        const val Banner = "ContentBannerSlider"
        const val Video = "ContentVideo"
        const val News = "ContentNews"
    }
}