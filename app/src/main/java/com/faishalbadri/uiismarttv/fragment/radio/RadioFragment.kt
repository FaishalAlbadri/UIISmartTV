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
    private var radioName = ""

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

    fun playRadio(link: String, radioName: String) {
        if (player == null) {
            initRadio(link, radioName)
        } else {
            binding.txtRadioPlay.visibility = View.GONE
            releasePlayer()
            if (lastRadio != link) {
                initRadio(link, radioName)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        radioAdapter.delete()
        releasePlayer()
    }

    private fun initRadio(link: String, radioName: String) {
        lastRadio = link
        this.radioName = radioName
        player = ExoPlayer.Builder(activity?.applicationContext!!).build().apply {
            addListener(playerListener)
        }
        val mediaItem = MediaItem.fromUri(link)
        player!!.setMediaItem(mediaItem)
        player!!.prepare()
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
                    binding.apply {
                        txtRadioPlay.text = "Kamu sedang mendengarkan " + radioName
                        txtRadioPlay.visibility = View.VISIBLE
                    }
                }
                Player.STATE_BUFFERING -> {
                    binding.apply {
                        txtRadioPlay.text = "Loading Radio"
                        txtRadioPlay.visibility = View.VISIBLE
                    }
                }
                Player.STATE_IDLE -> {
                }
            }
        }
    }
}
