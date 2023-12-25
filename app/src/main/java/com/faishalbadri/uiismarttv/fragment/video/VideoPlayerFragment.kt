package com.faishalbadri.uiismarttv.fragment.video

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.navigation.fragment.navArgs
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.databinding.FragmentVideoPlayerBinding
import com.faishalbadri.uiismarttv.utils.viewModelsFactory

@UnstableApi
@SuppressLint("RestrictedApi")
class VideoPlayerFragment : Fragment() {

    private var _binding: FragmentVideoPlayerBinding? = null
    val binding get() = _binding!!

    private lateinit var activityHome: HomeActivity

    private val args by navArgs<VideoPlayerFragmentArgs>()
    private val viewModel by viewModelsFactory {
        VideoPlayerViewModel(args.id, this.requireContext())
    }

    private var player: ExoPlayer? = null
    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityHome = getActivity() as HomeActivity
        activityHome.stopPlayer()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                VideoPlayerViewModel.State.Loading -> {}
                is VideoPlayerViewModel.State.SuccessLoadVideo -> displayVideo(state.link)
                is VideoPlayerViewModel.State.FailedLoadVideo -> {
                    Toast.makeText(
                        requireContext(),
                        state.error ?: "",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun displayVideo(link: String) {
        player = ExoPlayer.Builder(requireContext())
            .build()
            .apply {
                setMediaSource(
                    ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(Uri.parse(link))))
                prepare()
                addListener(playerListener)
            }
    }

    fun onBackPressed(): Boolean = when {

        else -> false
    }

    private fun releasePlayer(){
        player?.apply {
            playWhenReady = false
            release()
        }
        player = null
    }

    private fun pause(){
        player?.playWhenReady = false
    }

    private fun play(){
        player?.playWhenReady = true
    }

    private fun restartPlayer(){
        player?.seekTo(0)
        player?.playWhenReady = true
    }

    private val playerListener = object: Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when(playbackState){
                STATE_ENDED -> restartPlayer()
                STATE_READY -> {
                    binding.pvVideo.player = player
                    play()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    override fun onResume() {
        super.onResume()
        play()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}