package com.faishalbadri.uiismarttv.data.remote.video

import com.google.gson.annotations.SerializedName

data class VideoItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("img")
	val img: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("desc")
	val desc: String
)