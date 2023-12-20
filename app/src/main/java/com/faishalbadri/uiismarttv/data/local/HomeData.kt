package com.faishalbadri.uiismarttv.data.local

import com.faishalbadri.uiismarttv.adapter.AppAdapter

class HomeData(

    val msg: String,
    val list: List<AppAdapter.Item>

) : AppAdapter.Item {

    var selectedIndex = 0
    var itemSpacing: Int = 0

    override var itemType = AppAdapter.Type.ITEM_HOME

    companion object {
        const val Adzan = "Waktu Sholat"
        const val Banner = "Banner"
        const val Video = "Video Youtube UII"
        const val News = "Berita Terkini"
        const val PojokRektor = "Pojok Rektor"
    }
}