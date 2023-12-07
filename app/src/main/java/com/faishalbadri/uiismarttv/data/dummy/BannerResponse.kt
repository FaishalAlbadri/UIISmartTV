package com.faishalbadri.uiismarttv.data.dummy

import com.faishalbadri.uiismarttv.adapter.viewholder.AppAdapter

class BannerResponse(

    val msg: String,
    val list: List<AppAdapter.Item>

) : AppAdapter.Item {

    var selectedIndex = 0

    override lateinit var itemType: AppAdapter.Type
}