package com.faishalbadri.uiismarttv.fragment.radio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.RadioData
import com.faishalbadri.uiismarttv.adapter.RadioAdapter
import com.faishalbadri.uiismarttv.databinding.FragmentRadioBinding

@UnstableApi
class RadioFragment : Fragment() {

    private var _binding: FragmentRadioBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RadioViewModel>()
    private lateinit var radioAdapter: RadioAdapter

    private var player: ExoPlayer?= null
    private var lastRadio = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRadioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.radioData.observe(viewLifecycleOwner) {
            loadData(it)
        }
        viewModel.getRadio()
        binding.root.requestFocus()
    }

    private fun loadData(data: MutableList<RadioData>) {
        radioAdapter = RadioAdapter(data)
        binding.vgRadio.apply {
            adapter = radioAdapter
            setItemSpacing(requireContext().resources.getDimension(R.dimen.radio_spacing).toInt())
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    fun playRadio(link: String) {
        if (player == null) {
            lastRadio = link
            player = ExoPlayer.Builder(activity?.applicationContext!!).build().apply {
                addListener(playerListener)
            }
            val mediaItem = MediaItem.fromUri(link)
            player!!.setMediaItem(mediaItem)
            player!!.prepare()
        } else {
            releasePlayer()
            if (lastRadio != link) {
                lastRadio = link
                player = ExoPlayer.Builder(activity?.applicationContext!!).build().apply {
                    addListener(playerListener)
                }
                val mediaItem = MediaItem.fromUri(link)
                player!!.setMediaItem(mediaItem)
                player!!.prepare()
            }
        }

    }

    private fun releasePlayer() {
        player?.apply {
            playWhenReady = false
            stop()
            release()
        }
        player = null
    }

    private fun play() {
        player?.playWhenReady = true
    }


    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_ENDED -> {
                    releasePlayer()
                }
                Player.STATE_READY -> {
                    play()
                }
                Player.STATE_BUFFERING -> {
                }
                Player.STATE_IDLE -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        releasePlayer()
    }
}
