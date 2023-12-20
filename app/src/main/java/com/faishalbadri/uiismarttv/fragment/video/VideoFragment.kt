package com.faishalbadri.uiismarttv.fragment.video

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.local.Video
import com.faishalbadri.uiismarttv.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    private var _binding: FragmentVideoBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<VideoViewModel>()
    private val appAdapter = AppAdapter()
    private lateinit var activityHome: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                VideoViewModel.State.Loading -> showLoading(true)
                is VideoViewModel.State.SuccessLoadVideo -> {
                    showLoading(false)
                    loadData(state.data)
                }

                is VideoViewModel.State.FailedLoadVideo -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        state.error.message ?: "",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
        setView()
    }

    private fun setView() {
        activityHome = getActivity() as HomeActivity
        binding.vgvVideo.apply {
            adapter = appAdapter
            setItemSpacing(resources.getDimension(R.dimen.home_spacing).toInt() * 2)
        }
        binding.root.requestFocus()
    }

    private fun loadData(news: List<Video>) {
        appAdapter.submitList(news.onEach {
            it.itemType = AppAdapter.Type.ITEM_VIDEO_VERTICAL
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (activityHome.player != null && activityHome.dataRadio != null) {
            binding.txtListening.visibility = View.VISIBLE
            binding.txtListening.text =
                "Now you're listening to " + activityHome.dataRadio!!.namaRadio
        } else {
            binding.txtListening.visibility = View.GONE
        }
    }
}