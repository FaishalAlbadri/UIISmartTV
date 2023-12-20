package com.faishalbadri.uiismarttv.data.remote.home

import com.google.gson.annotations.SerializedName

data class AdzanItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("waktu")
	val waktu: String
)