package com.faishalbadri.uiismarttv.data.remote.adzan

import com.google.gson.annotations.SerializedName

data class AdzanResponse(

	@field:SerializedName("adzan")
	val adzan: List<AdzanItem?>? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("key")
	val key: String? = null
)