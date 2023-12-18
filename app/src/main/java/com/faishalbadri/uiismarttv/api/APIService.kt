package com.faishalbadri.uiismarttv.api

import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.Video

object APIService {

    private val service = APIInterface.build()

    suspend fun getHome(): List<HomeData> {
        val home = service.getHome()
        var homeData: List<HomeData> = mutableListOf()
        if (home.isSuccessful) {
            val homeResponse = home.body()
            val listBanner = homeResponse!!.banner
            val listNews = homeResponse!!.news
            val listPojokRektor = homeResponse!!.pojokRektor

            val dataBanner: MutableList<Banner> = ArrayList()
            if (listBanner!!.isNotEmpty()) {
                listBanner.forEach {
                    dataBanner.add(
                        Banner(
                            id = "",
                            title = "",
                            desc = "",
                            img = it!!.img,
                            link = it.link
                        )
                    )
                }
            }

            val dataNews: MutableList<News> = ArrayList()
            if (listNews!!.isNotEmpty()) {
                listNews.forEach {
                    dataNews.add(
                        News(
                            id = it!!.id,
                            title = it.title,
                            date = it.date,
                            desc = "",
                            img = it.img
                        )
                    )
                }
            }
            dataNews.add(News("", HomeData.News, "", "", ""))

            val dataPojokRektor: MutableList<News> = ArrayList()
            if (listPojokRektor!!.isNotEmpty()) {
                listPojokRektor.forEach {
                    dataPojokRektor.add(
                        News(
                            id = it!!.id,
                            title = it.title,
                            date = it.date,
                            desc = "",
                            img = it.img
                        )
                    )
                }
            }
            dataPojokRektor.add(News("", HomeData.PojokRektor, "", "", ""))

            val dataVideo: MutableList<Video> = ArrayList()
            for (i in 0 until 5) {
                dataVideo.add(DummyData().dataVideo.get(i))
            }
            dataVideo.add(Video("", "", "", "", ""))
            homeData = mutableListOf(
                HomeData(
                    msg = HomeData.Banner,
                    list = dataBanner
                ),
                HomeData(
                    msg = HomeData.Video,
                    list = dataVideo
                ),
                HomeData(
                    msg = HomeData.News,
                    list = dataNews
                ),
                HomeData(
                    msg = HomeData.PojokRektor,
                    list = dataPojokRektor
                )
            )
        }
        return homeData
    }

    suspend fun getNews(page: Int = 1): List<News> {
        val news = service.getNews(page)
        val newsData: MutableList<News> = mutableListOf()
        if (news.isSuccessful) {
            news.body()!!.forEach {
                newsData.add(
                    News(
                        id = it.id, title = it.title, date = it.date, desc = it.desc, img = it.img
                    )
                )
            }
        }
        return newsData
    }

    suspend fun getPojokRektor(page: Int = 1): List<News> {
        val news = service.getPojokRektor(page)
        val newsData: MutableList<News> = mutableListOf()
        if (news.isSuccessful) {
            news.body()!!.forEach {
                newsData.add(
                    News(
                        id = it.id, title = it.title, date = it.date, desc = it.desc, img = it.img
                    )
                )
            }
        }
        return newsData
    }
}