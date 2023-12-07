package com.faishalbadri.uiismarttv.data.dummy

import com.faishalbadri.uiismarttv.adapter.viewholder.AppAdapter

sealed class Show : AppAdapter.Item

class Video(
    val id: String,
    val title: String,
    val desc: String,
    val img: String,
    val link: String
) : Show(), AppAdapter.Item, Cloneable {

    override lateinit var itemType: AppAdapter.Type

    public override fun clone() = super.clone() as Video
}

class News(
    val id: String,
    val title: String,
    val desc: String,
    val img: String
) : Show(), AppAdapter.Item, Cloneable {

    override lateinit var itemType: AppAdapter.Type

    public override fun clone() = super.clone() as News
}

class Banner(
    val id: String,
    val title: String,
    val desc: String,
    val img: String
) : Show(), AppAdapter.Item, Cloneable {

    override lateinit var itemType: AppAdapter.Type

    public override fun clone() = super.clone() as Banner
}