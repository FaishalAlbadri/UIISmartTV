package com.faishalbadri.uiismarttv.data.local

import com.faishalbadri.uiismarttv.adapter.AppAdapter

class RadioData(
    val namaRadio: String,
    val signalRadio: String,
    val imageRadio: String,
    val link: String
) : AppAdapter.Item, Cloneable {
    override lateinit var itemType: AppAdapter.Type
    public override fun clone() = super.clone() as RadioData
}