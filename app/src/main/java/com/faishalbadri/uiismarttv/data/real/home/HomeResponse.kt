package com.faishalbadri.uiismarttv.data.real.home

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("news")
	val news: List<NewsItem?>? = null,

	@field:SerializedName("banner")
	val banner: List<BannerItem?>? = null,

	@field:SerializedName("pojok_rektor")
	val pojokRektor: List<PojokRektorItem?>? = null
)