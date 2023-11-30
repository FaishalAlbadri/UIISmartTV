package com.faishalbadri.uiismarttv.fragment.radio

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.RadioData
import com.faishalbadri.uiismarttv.adapter.RadioAdapter
import com.faishalbadri.uiismarttv.databinding.FragmentRadioBinding
import kotlinx.coroutines.launch

class RadioFragment : Fragment() {

    private var _binding: FragmentRadioBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RadioViewModel>()
    private lateinit var radioAdapter: RadioAdapter

    private var mediaPlayer: MediaPlayer? = null

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
        viewLifecycleOwner.lifecycleScope.launch {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(activity, Uri.parse(link))
            }
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer!!.release()
                mediaPlayer = null
            } else {
                mediaPlayer?.start()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mediaPlayer!!.release()
    }
}
