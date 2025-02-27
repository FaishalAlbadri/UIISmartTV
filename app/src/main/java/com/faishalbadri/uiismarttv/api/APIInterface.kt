package com.faishalbadri.uiismarttv.api

import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.data.local.News
import com.faishalbadri.uiismarttv.data.remote.home.HomeResponse
import com.faishalbadri.uiismarttv.data.remote.video.VideoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Date
import java.util.concurrent.TimeUnit


interface APIInterface {

    companion object {
        val url = "https://uiitv.000webhostapp.com/"
        fun build(): APIInterface {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(APIInterface::class.java)
        }
    }

    @FormUrlEncoded
    @POST("home/")
    suspend fun getHome(
        @Field("id") id: String
    ): Response<HomeResponse>

    @FormUrlEncoded
    @POST("videos/")
    suspend fun getVideo(
        @Field("maxResult") maxResult: Int,
    ): Response<VideoResponse>

    @FormUrlEncoded
    @POST("news/")
    suspend fun getNews(
        @Field("page") page: Int,
    ): Response<List<News>>

    @FormUrlEncoded
    @POST("news/pojokrektor")
    suspend fun getPojokRektor(
        @Field("page") page: Int,
    ): Response<List<News>>

    @FormUrlEncoded
    @POST("news/detail")
    suspend fun getDetailNews(
        @Field("id") id: String,
    ): Response<List<News>>

    @GET("adzan/lokasi")
    suspend fun getLocation(): Response<List<Adzan>>

    @FormUrlEncoded
    @POST("search/")
    suspend fun getSeach(
        @Field("search") search: String,
    ): Response<HomeResponse>
}