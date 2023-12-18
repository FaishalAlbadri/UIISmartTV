package com.faishalbadri.uiismarttv.api.scrapper.uii

import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.DummyData
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.Video

object APIServiceUII {

    private val service = APIInterfaceUII.build()

    suspend fun getHome(): List<HomeData> {

        val document = service.getHome()
        var countData = 0

        val dataVideo: MutableList<Video> = ArrayList()
        val dataBanner: MutableList<Banner> = ArrayList()
        val dataNews: MutableList<News> = ArrayList()
        val dataPojokRektor: MutableList<News> = ArrayList()

        document.select("div.ls-slide").map {
            if (it.selectFirst("img")!!.attr("class").equals("ls-tn")) {
                val img = it.selectFirst("img.ls-img-layer")
                    ?.attr("src") ?: ""
                dataBanner.add(
                    Banner(
                        id = "",
                        title = "",
                        desc = "",
                        img = img,
                        link = ""
                    )
                )
            }
        }

        document.select("div.bdpp-post-grid").map {
            countData += 1
            val title = it.selectFirst("h2.bdpp-post-title")
                ?.text() ?: ""
            val img = it.selectFirst("div.bdpp-post-img-bg")?.attr("style")
                ?.replace("background-image:url(", "")?.replace(")", "") ?: ""
            val date = it.selectFirst("span")!!.text()
            if (countData <= 3) {
                val id = it.selectFirst("a")
                    ?.attr("href")?.replace("https://www.uii.ac.id/", "")?.replace("/", "") ?: ""
                dataNews.add(
                    News(
                        id = id,
                        title = title,
                        date= date,
                        desc = "",
                        img = img
                    )
                )
            } else {
                val id = it.selectFirst("h2.bdpp-post-title > a")
                    ?.attr("href")?.replace("https://www.uii.ac.id/", "")?.replace("/", "") ?: ""
                dataPojokRektor.add(
                    News(
                        id = id,
                        title = title,
                        date= date,
                        desc = "",
                        img = img
                    )
                )
            }
        }

        for (i in 0 until 5) {
            dataVideo.add(DummyData().dataVideo.get(i))
        }
        dataVideo.add(Video("", "", "", "", ""))
        dataNews.add(News("", HomeData.News, "", "",""))
        dataPojokRektor.add(News("", HomeData.PojokRektor, "", "",""))

        val homeData = mutableListOf(
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

        return homeData
    }

    suspend fun getNews(page: Int = 1): List<News> {
        val document = service.getNews(page)
        val newsData =
            document.select("div.bdpp-post-list-main")
                .map {
                    News(
                        id = it.selectFirst("div.bdpp-post-img-bg > a")
                            ?.attr("href")?.replace("https://www.uii.ac.id/", "")?.replace("/", "")
                            ?: "",
                        title = it.selectFirst("h2.bdpp-post-title")
                            ?.text() ?: "",
                        date= it.selectFirst("span")!!.text(),
                        desc = it.selectFirst("div.bdpp-post-content > div.bdpp-post-desc")
                            ?.text() ?: "",
                        img = it.selectFirst("div.bdpp-post-img-bg img")
                            ?.attr("src") ?: ""
                    )
                }
        return newsData
    }

    suspend fun getPojokRektor(page: Int = 1): List<News> {
        val document = service.getPojokRektor(page)
        val newsData =
            document.select("div.bdpp-post-grid")
                .map {
                    News(
                        id = it.selectFirst("h2.bdpp-post-title a")
                            ?.attr("href")?.replace("https://www.uii.ac.id/", "")?.replace("/", "")
                            ?: "",
                        title = it.selectFirst("h2.bdpp-post-title")
                            ?.text() ?: "",
                        date= it.selectFirst("span")!!.text(),
                        desc = it.selectFirst("div.bdpp-post-desc")
                            ?.text() ?: "",
                        img = it.selectFirst("div.bdpp-post-img-bg")?.attr("style")
                            ?.replace("background-image:url(", "")?.replace(")", "") ?: ""
                    )
                }
        return newsData
    }
}