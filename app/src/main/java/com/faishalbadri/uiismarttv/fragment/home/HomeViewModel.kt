package com.faishalbadri.uiismarttv.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.BannerResponse
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.Video

class HomeViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _videoData = MutableLiveData<List<Video>>()
    val videoData: LiveData<List<Video>> = _videoData

    private val _newsData = MutableLiveData<List<News>>()
    val newsData: LiveData<List<News>> = _newsData

    private val _bannerData = MutableLiveData<List<BannerResponse>>()
    val bannerData: LiveData<List<BannerResponse>> = _bannerData

    fun getVideo(){
        _isLoading.value = true

        var data:List<Video> = listOf(
            Video(
                id = "1",
                title = "International Mobility Program through Educational and Company Visit in Seoul, South Korea",
                desc = "International Mobility Program through Educational and Company Visit in Seoul, South Korea \uD83C\uDDF0\uD83C\uDDF7 It is an activity to visit several companies and universities abroad. So students gain direct experience and in-depth understanding of industrial operations at the international level as well and build interaction and collaboration with campuses abroad. We have many destination for studying in Korea, some of which are Smart City Korea Association, King Sejong Museum, Hanyang University Campus Erica, Ginseng Museum, Hyundai Motor Studio Seoul, and many more✨ Very interesting, right?! \uD83E\uDD29 Let's join The International Program in Informatics to get lots of opportunities to explore the international world!",
                img = "https://i.ytimg.com/vi/hz6aA-69Rpo/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBiz0TYjbt7CWvG1TE7RkZfb2Tu5g",
                link = "https://youtu.be/hz6aA-69Rpo"
            ),
            Video(
                id = "2",
                title = "Profil Jurusan Informatika Universitas Islam Indonesia",
                desc = "No description has been added to this video.",
                img = "https://i.ytimg.com/vi/jcViIMwAL1Q/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLA52EIx9Ou3tbAGdNr3785Neo7vAg",
                link = "https://youtu.be/jcViIMwAL1Q"
            ),
            Video(
                id = "3",
                title = "International Program In Informatics Universitas Islam Indonesia",
                desc = "No description has been added to this video.",
                img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ",
                link = "https://youtu.be/F9PedD7Crd4"
            )
        )
        _videoData.value = data
        getNews()
    }

    fun getNews(){
        var data:List<News> = listOf(
            News(
                id = "1",
                title = "International Mobility Program through Educational and Company Visit in Seoul, South Korea",
                desc = "International Mobility Program through Educational and Company Visit in Seoul, South Korea \uD83C\uDDF0\uD83C\uDDF7 It is an activity to visit several companies and universities abroad. So students gain direct experience and in-depth understanding of industrial operations at the international level as well and build interaction and collaboration with campuses abroad. We have many destination for studying in Korea, some of which are Smart City Korea Association, King Sejong Museum, Hanyang University Campus Erica, Ginseng Museum, Hyundai Motor Studio Seoul, and many more✨ Very interesting, right?! \uD83E\uDD29 Let's join The International Program in Informatics to get lots of opportunities to explore the international world!",
                img = "https://i.ytimg.com/vi/hz6aA-69Rpo/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBiz0TYjbt7CWvG1TE7RkZfb2Tu5g"
            ),
            News(
                id = "2",
                title = "Profil Jurusan Informatika Universitas Islam Indonesia",
                desc = "No description has been added to this video.",
                img = "https://i.ytimg.com/vi/jcViIMwAL1Q/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLA52EIx9Ou3tbAGdNr3785Neo7vAg"
            ),
            News(
                id = "3",
                title = "International Program In Informatics Universitas Islam Indonesia",
                desc = "No description has been added to this video.",
                img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ"
            )
        )
        _newsData.value = data

        getBanner()
    }

    fun getBanner() {
        var data:List<Banner> = listOf(
            Banner(
                id = "1",
                title = "International Mobility Program through Educational and Company Visit in Seoul, South Korea",
                desc = "International Mobility Program through Educational and Company Visit in Seoul, South Korea \uD83C\uDDF0\uD83C\uDDF7 It is an activity to visit several companies and universities abroad. So students gain direct experience and in-depth understanding of industrial operations at the international level as well and build interaction and collaboration with campuses abroad. We have many destination for studying in Korea, some of which are Smart City Korea Association, King Sejong Museum, Hanyang University Campus Erica, Ginseng Museum, Hyundai Motor Studio Seoul, and many more✨ Very interesting, right?! \uD83E\uDD29 Let's join The International Program in Informatics to get lots of opportunities to explore the international world!",
                img = "https://i.ytimg.com/vi/hz6aA-69Rpo/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLBiz0TYjbt7CWvG1TE7RkZfb2Tu5g"
            ),
            Banner(
                id = "2",
                title = "Profil Jurusan Informatika Universitas Islam Indonesia",
                desc = "No description has been added to this video.",
                img = "https://i.ytimg.com/vi/jcViIMwAL1Q/hqdefault.jpg?sqp=-oaymwEcCNACELwBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLA52EIx9Ou3tbAGdNr3785Neo7vAg"
            ),
            Banner(
                id = "3",
                title = "International Program In Informatics Universitas Islam Indonesia",
                desc = "No description has been added to this video.",
                img = "https://i.ytimg.com/vi/F9PedD7Crd4/hqdefault.jpg?sqp=-oaymwE2CNACELwBSFXyq4qpAygIARUAAIhCGAFwAcABBvABAfgB_gmAAtAFigIMCAAQARhlIGUoZTAP&rs=AOn4CLABNsetp71hts_VBEQrAmZI_np9YQ"
            )
        )

        var dataResponse: List<BannerResponse> = listOf(
            BannerResponse(
                msg = "Berhasil",
                list = data
            )
        )
        _bannerData.value = dataResponse
        _isLoading.value = false
    }

}