package com.faishalbadri.uiismarttv.api

import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.data.local.Banner
import com.faishalbadri.uiismarttv.data.local.HomeData
import com.faishalbadri.uiismarttv.data.local.News
import com.faishalbadri.uiismarttv.data.local.Video
import com.faishalbadri.uiismarttv.utils.capitalizeWords
import java.util.Calendar

object APIService {

    private val service = APIInterface.build()

    suspend fun getSearch(search: String): List<HomeData> {
        val service = service.getSeach(search)
        var searchData: List<HomeData> = mutableListOf()

        if (service.isSuccessful) {
            val searchResponse = service.body()
            val listVideo = searchResponse!!.video
            val listNews = searchResponse.news

            val dataNews: MutableList<News> = ArrayList()
            if (listNews!!.isNotEmpty()) {
                listNews.forEach {
                    dataNews.add(
                        News(
                            id = it!!.id,
                            title = it.title,
                            date = it.date,
                            desc = it.desc,
                            img = it.img
                        )
                    )
                }
            }

            val dataVideo: MutableList<Video> = ArrayList()
            if (listVideo!!.isNotEmpty()) {
                listVideo.forEach {
                    dataVideo.add(
                        Video(
                            id = it!!.id,
                            title = it.title,
                            desc = it.desc,
                            img = it.img,
                            link = it.link,
                            date = it.date,
                        )
                    )
                }
            }

            searchData = mutableListOf(
                HomeData(
                    msg = HomeData.Video,
                    list = dataVideo
                ),
                HomeData(
                    msg = HomeData.News,
                    list = dataNews
                )

            )
        }

        return searchData
    }

    suspend fun getHome(id: String, nama: String): List<HomeData> {
        val home = service.getHome(id)
        var homeData: List<HomeData> = mutableListOf()
        if (home.isSuccessful) {
            val homeResponse = home.body()
            val listBanner = homeResponse!!.banner
            val listNews = homeResponse.news
            val listPojokRektor = homeResponse.pojokRektor
            val listVideo = homeResponse.video
            val listAdzan = homeResponse.adzan

            val dataAdzan: MutableList<Adzan> = ArrayList()
            if (listAdzan!!.isNotEmpty()) {
                listAdzan.forEach {
                    dataAdzan.add(
                        Adzan(
                            id = it!!.nama,
                            value = it.waktu
                        )
                    )
                }
            }

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
            if (listVideo!!.isNotEmpty()) {
                listVideo.forEach {
                    dataVideo.add(
                        Video(
                            id = it!!.id,
                            title = it.title,
                            desc = it.desc,
                            img = it.img,
                            link = it.link,
                            date = it.date,
                        )
                    )
                }
            }
            dataVideo.add(Video("", "", "", "", "", ""))

            homeData = mutableListOf(
                HomeData(
                    msg = HomeData.Banner,
                    list = dataBanner
                ),
                HomeData(
                    msg = HomeData.Adzan + " " + nama.capitalizeWords(),
                    list = dataAdzan
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

    suspend fun getVideo(maxResult: Int = 50): List<Video> {
        val video = service.getVideo(maxResult)
        val videoData: MutableList<Video> = mutableListOf()
        if (video.isSuccessful) {
            video.body()!!.video!!.forEach {
                videoData.add(
                    Video(
                        id = it!!.id,
                        title = it.title,
                        desc = it.desc,
                        img = it.img,
                        link = it.link,
                        date = it.date,
                    )
                )
            }
        }
        return videoData
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

    suspend fun getLocation(): List<Adzan> {
        val location = service.getLocation()
        val locationData: MutableList<Adzan> = mutableListOf()
        if (location.isSuccessful) {
            location.body()!!.sortedBy { it.value }.forEach {
                locationData.add(
                    Adzan(
                        id = it.id,
                        value = it.value
                    )
                )
            }
        }
        return locationData
    }

    suspend fun getDetailNews(id: String): News {
        val news = service.getDetailNews(id)
        var newsData: News? = null
        if (news.isSuccessful) {
            news.body()!!.forEach {
                newsData = it
            }
        }
        return newsData!!
    }
}