package com.faishalbadri.uiismarttv

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.NavHostFragment
import com.faishalbadri.navigation.setupWithNavController
import com.faishalbadri.uiismarttv.data.dummy.RadioData
import com.faishalbadri.uiismarttv.databinding.ActivityHomeBinding
import com.faishalbadri.uiismarttv.databinding.ContentHeaderMenuMainBinding
import com.faishalbadri.uiismarttv.fragment.news.NewsDetailFragment
import com.faishalbadri.uiismarttv.fragment.profile.ProfileFragment
import com.faishalbadri.uiismarttv.fragment.radio.RadioFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import java.util.Locale

class HomeActivity : FragmentActivity(), TextToSpeech.OnInitListener {

    private var _binding: ActivityHomeBinding? = null
    val binding: ActivityHomeBinding get() = _binding!!

    var player: ExoPlayer? = null
    var dataRadio: RadioData? = null
    var textToSpeech: TextToSpeech? = null
    var textToSpeechStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = this.supportFragmentManager
            .findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.home)

        binding.navMain.setupWithNavController(navController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.navMainFragment.isFocusedByDefault = true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.navMain.headerView?.apply {
                val header = ContentHeaderMenuMainBinding.bind(this)

                setOnOpenListener {
                    header.tvNavigationHeaderTitle.visibility = View.VISIBLE
                }
                setOnCloseListener {
                    header.tvNavigationHeaderTitle.visibility = View.GONE
                }

                setOnClickListener {
                    navController.navigate(NavMainGraphDirections.actionGlobalProfile())
                }
            }

            when (destination.id) {
                R.id.search,
                R.id.home,
                R.id.gallery -> {
                    binding.navMain.visibility = View.VISIBLE
                    if (player != null && dataRadio != null) {
                        binding.layoutListening.visibility = View.VISIBLE
                    }
                }
                R.id.radio -> {
                    binding.navMain.visibility = View.VISIBLE
                    binding.layoutListening.visibility = View.GONE
                }

                R.id.news,
                R.id.video -> {
                    binding.navMain.visibility = View.GONE
                    binding.layoutListening.visibility = View.GONE
                }
                else -> {
                    binding.navMain.visibility = View.GONE
                    if (player != null && dataRadio != null) {
                        binding.layoutListening.visibility = View.VISIBLE
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (navController.currentDestination?.id) {
                    R.id.home -> when {
                        binding.navMain.hasFocus() -> finish()
                        else -> binding.navMain.requestFocus()
                    }
                    R.id.search,
                    R.id.radio,
                    R.id.gallery -> when {
                        binding.navMain.hasFocus() -> binding.navMain.findViewById<View>(R.id.home)
                            .let {
                                it.requestFocus()
                                it.performClick()
                            }

                        else -> binding.navMain.requestFocus()
                    }
                    else -> {
                        when (val currentFragment = getCurrentFragment()) {
                            is ProfileFragment -> currentFragment.onBackPressed()
                            else -> false
                        }.takeIf { !it }?.let {
                            navController.navigateUp()
                        }
                    }
                }
            }
        })
        textToSpeech = TextToSpeech(this, this)
        textToSpeech!!.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) {
                when (val currentFragment = getCurrentFragment()) {
                    is NewsDetailFragment -> currentFragment.setButtonPlayTextToSpeech()
                    else -> false
                }
            }
            override fun onError(utteranceId: String?) {}
        })
        textToSpeech!!.setSpeechRate(0.7f)
    }

    fun initTextToSpeech(text: String){
        stopPlayer()
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    fun initRadio(data: RadioData) {
        dataRadio = data
        player = ExoPlayer.Builder(this).build().apply {
            addListener(playerListener)
        }
        val mediaItem = MediaItem.fromUri(data.link)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
    }

    fun releasePlayer() {
        player?.apply {
            playWhenReady = false
            stop()
            release()
        }
        player = null
        binding.layoutListening.visibility = View.GONE
    }

    private fun play() {
        player?.playWhenReady = true
        if (player != null && dataRadio != null) {
            binding.txtListening.text = "Now you're listening to " + dataRadio!!.namaRadio
        }
    }

    private val playerListener = object : Player.Listener {
        @OptIn(UnstableApi::class)
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_ENDED -> {
                    releasePlayer()
                }

                Player.STATE_READY -> {
                    when (val currentFragment = getCurrentFragment()) {
                        is RadioFragment -> currentFragment.setStateReady(dataRadio!!)
                        else -> false
                    }
                    play()
                }

                Player.STATE_BUFFERING -> {
                    when (val currentFragment = getCurrentFragment()) {
                        is RadioFragment -> currentFragment.setStateBuffering()
                        else -> false
                    }
                }

                Player.STATE_IDLE -> {
                }
            }
        }
    }

    fun stopPlayer() {
        releasePlayer()
        dataRadio = null
        when (val currentFragment = getCurrentFragment()) {
            is RadioFragment -> currentFragment.setStateStop()
            else -> false
        }
    }

    fun stopTextToSpeech(){
        textToSpeech!!.stop()
        when (val currentFragment = getCurrentFragment()) {
            is NewsDetailFragment -> currentFragment.setButtonPlayTextToSpeech()
            else -> false
        }
    }

    fun destroyTextToSpeech() {
        if (textToSpeech != null) {
            stopTextToSpeech()
            textToSpeech!!.shutdown()
        }
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
        stopTextToSpeech()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayer()
        destroyTextToSpeech()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech!!.setLanguage(Locale("id","ID"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                textToSpeechStatus = false
            } else {
                textToSpeechStatus = true
            }
        }
    }
}