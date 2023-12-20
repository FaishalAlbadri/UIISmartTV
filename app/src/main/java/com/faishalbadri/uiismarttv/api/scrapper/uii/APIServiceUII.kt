package com.faishalbadri.uiismarttv.api.scrapper.uii

import com.faishalbadri.uiismarttv.data.local.Banner
import com.faishalbadri.uiismarttv.data.local.HomeData
import com.faishalbadri.uiismarttv.data.local.News
import com.faishalbadri.uiismarttv.data.local.Video

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
            dataVideo.add(DummyDataVideo.get(i))
        }
        dataVideo.add(Video("", "", "", "", "",""))
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

    var DummyDataVideo: List<Video> = listOf(
        Video(
            id = "1",
            title = "International Mobility Program through Educational and Company Visit in Seoul, South Korea",
            desc = "International Mobility Program through Educational and Company Visit in Seoul, South Korea \uD83C\uDDF0\uD83C\uDDF7 It is an activity to visit several companies and universities abroad. So students gain direct experience and in-depth understanding of industrial operations at the international level as well and build interaction and collaboration with campuses abroad. We have many destination for studying in Korea, some of which are Smart City Korea Association, King Sejong Museum, Hanyang University Campus Erica, Ginseng Museum, Hyundai Motor Studio Seoul, and many more✨ Very interesting, right?! \uD83E\uDD29 Let's join The International Program in Informatics to get lots of opportunities to explore the international world!",
            img = "https://i.ytimg.com/vi/hz6aA-69Rpo/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBiz0TYjbt7CWvG1TE7RkZfb2Tu5g",
            link = "https://youtu.be/hz6aA-69Rpo",
            ""
        ),
        Video(
            id = "2",
            title = "Profil Jurusan Informatika Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/jcViIMwAL1Q/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLA52EIx9Ou3tbAGdNr3785Neo7vAg",
            link = "https://youtu.be/jcViIMwAL1Q",
            ""
        ),
        Video(
            id = "3",
            title = "International Program In Informatics Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
            link = "https://youtu.be/F9PedD7Crd4",
            ""
        ),
        Video(
            id = "4",
            title = "International Program In Informatics Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
            link = "https://youtu.be/F9PedD7Crd4",
            ""
        ),
        Video(
            id = "5",
            title = "International Program In Informatics Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
            link = "https://youtu.be/F9PedD7Crd4",
            ""
        ),Video(
            id = "6",
            title = "International Mobility Program through Educational and Company Visit in Seoul, South Korea",
            desc = "International Mobility Program through Educational and Company Visit in Seoul, South Korea \uD83C\uDDF0\uD83C\uDDF7 It is an activity to visit several companies and universities abroad. So students gain direct experience and in-depth understanding of industrial operations at the international level as well and build interaction and collaboration with campuses abroad. We have many destination for studying in Korea, some of which are Smart City Korea Association, King Sejong Museum, Hanyang University Campus Erica, Ginseng Museum, Hyundai Motor Studio Seoul, and many more✨ Very interesting, right?! \uD83E\uDD29 Let's join The International Program in Informatics to get lots of opportunities to explore the international world!",
            img = "https://i.ytimg.com/vi/hz6aA-69Rpo/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBiz0TYjbt7CWvG1TE7RkZfb2Tu5g",
            link = "https://youtu.be/hz6aA-69Rpo",
            ""
        ),
        Video(
            id = "7",
            title = "Profil Jurusan Informatika Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/jcViIMwAL1Q/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLA52EIx9Ou3tbAGdNr3785Neo7vAg",
            link = "https://youtu.be/jcViIMwAL1Q",
            ""
        ),
        Video(
            id = "8",
            title = "International Program In Informatics Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
            link = "https://youtu.be/F9PedD7Crd4",
            ""
        ),
        Video(
            id = "9",
            title = "International Program In Informatics Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
            link = "https://youtu.be/F9PedD7Crd4",
            ""
        ),
        Video(
            id = "10",
            title = "International Program In Informatics Universitas Islam Indonesia",
            desc = "No description has been added to this video.",
            img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
            link = "https://youtu.be/F9PedD7Crd4",
            ""
        )
    )
}