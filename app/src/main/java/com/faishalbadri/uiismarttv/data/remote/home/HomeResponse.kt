package com.faishalbadri.uiismarttv.data.remote.home

import com.faishalbadri.uiismarttv.data.remote.video.VideoItem
import com.google.gson.annotations.SerializedName

data class HomeResponse(

    @field:SerializedName("adzan")
    val adzan: List<AdzanItem?>? = null,

    @field:SerializedName("video")
    val video: List<VideoItem?>? = null,

    @field:SerializedName("news")
    val news: List<NewsItem?>? = null,

    @field:SerializedName("banner")
    val banner: List<BannerItem?>? = null,

    @field:SerializedName("pojok_rektor")
    val pojokRektor: List<PojokRektorItem?>? = null
)