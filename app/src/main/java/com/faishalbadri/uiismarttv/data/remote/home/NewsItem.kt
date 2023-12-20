package com.faishalbadri.uiismarttv.data.remote.home

import com.google.gson.annotations.SerializedName

data class NewsItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String
)