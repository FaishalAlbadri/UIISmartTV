package com.faishalbadri.uiismarttv.data.real.home

import com.google.gson.annotations.SerializedName

data class BannerItem(

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("link")
	val link: String
)