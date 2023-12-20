package com.faishalbadri.uiismarttv.data.remote.video

import com.google.gson.annotations.SerializedName

data class VideoResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("video")
	val video: List<VideoItem?>? = null
)