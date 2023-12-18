package com.faishalbadri.uiismarttv.api.scrapper.uii

import com.faishalbadri.uiismarttv.utils.jsoup.JsoupConverterFactory
import okhttp3.OkHttpClient
import org.jsoup.nodes.Document
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface APIInterfaceUII {

    companion object {
        val url = "https://www.uii.ac.id/"
        fun build(): APIInterfaceUII {
            val client = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JsoupConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(APIInterfaceUII::class.java)
        }
    }

    @GET(".")
    suspend fun getHome(): Document

    @GET("berita/page/{page}/")
    suspend fun getNews(@Path("page") page: Int): Document

    @GET("pojok-rektor/page/{page}")
    suspend fun getPojokRektor(@Path("page") page: Int): Document

}