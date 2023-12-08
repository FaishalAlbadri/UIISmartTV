package com.faishalbadri.uiismarttv.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.viewholder.AppAdapter
import com.faishalbadri.uiismarttv.data.dummy.BannerResponse
import com.faishalbadri.uiismarttv.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val appAdapter = AppAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeHome()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.bannerData.observe(viewLifecycleOwner) {
            displayContent(it)
        }
        viewModel.getVideo()
    }

    private fun initializeHome() {
        binding.vgvHome.apply {
            adapter = appAdapter
            setItemSpacing(resources.getDimension(R.dimen.home_spacing).toInt() * 2)
        }

        binding.root.requestFocus()
    }

    private fun displayContent(home: List<BannerResponse>) {
        appAdapter.items.apply {
            clear()

            //Banner Content
            home.find { it.msg == "Berhasil" }
                ?.let { bannerResponse ->
                    bannerResponse.itemType = AppAdapter.Type.SLIDER
                    add(bannerResponse)
                }

        }
        appAdapter.notifyDataSetChanged()
    }

    fun toNavigate() {
        (activity as HomeActivity).binding.navMain.requestFocus()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }
}