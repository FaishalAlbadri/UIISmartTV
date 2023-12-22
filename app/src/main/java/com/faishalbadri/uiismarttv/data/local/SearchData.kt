package com.faishalbadri.uiismarttv.data.local

import com.faishalbadri.uiismarttv.adapter.AppAdapter

class SearchData(

    val msg: String,
    val list: List<AppAdapter.Item>

) : AppAdapter.Item {

    var itemSpacing: Int = 0

    override var itemType = AppAdapter.Type.SEARCH

    companion object {
        const val Video = "Video Youtube UII"
        const val News = "Berita UII"
    }
}