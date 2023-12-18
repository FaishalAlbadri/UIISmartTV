package com.faishalbadri.uiismarttv.api.uii

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
            if (countData <= 3) {
                val id = it.selectFirst("a")
                    ?.attr("href") ?: ""
                dataNews.add(
                    News(
                        id = id,
                        title = title,
                        desc = "",
                        img = img
                    )
                )
            } else {
                val id = it.selectFirst("h2.bdpp-post-title > a")
                    ?.attr("href") ?: ""
                dataPojokRektor.add(
                    News(
                        id = id,
                        title = title,
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
        dataNews.add(News("", "", "", ""))
        dataPojokRektor.add(News("", "", "", ""))

        val homeData = mutableListOf(
            HomeData(
                msg = HomeData.Banner,
                document = document.toString(),
                list = dataBanner
            ),
            HomeData(
                msg = HomeData.Video,
                document = document.toString(),
                list = dataVideo
            ),
            HomeData(
                msg = HomeData.News,
                document = document.toString(),
                list = dataNews
            ),
            HomeData(
                msg = HomeData.PojokRektor,
                document = document.toString(),
                list = dataPojokRektor
            )
        )

        return homeData
    }
}