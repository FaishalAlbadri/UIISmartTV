package com.faishalbadri.uiismarttv.data.remote.adzan

import com.google.gson.annotations.SerializedName

data class AdzanItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null
)