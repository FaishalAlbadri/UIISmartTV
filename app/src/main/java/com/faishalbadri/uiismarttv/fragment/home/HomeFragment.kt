package com.faishalbadri.uiismarttv.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.Video
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

        viewModel.contentData.observe(viewLifecycleOwner) {
            displayContent(it)
        }
        viewModel.getContent()
    }

    private fun initializeHome() {
        binding.vgvHome.apply {
            adapter = appAdapter
            setItemSpacing(resources.getDimension(R.dimen.home_spacing).toInt() * 2)
        }

        binding.root.requestFocus()
    }

    private fun displayContent(home: List<HomeData>) {
        appAdapter.items.apply {
            clear()

            //Banner Content
            home.find { it.msg == HomeData.Banner }
                ?.let { homeData ->
                    homeData.itemType = AppAdapter.Type.HOME
                    add(homeData)
                }

            //Video Content
            home.find { it.msg == HomeData.Video }
                ?.let { homeData ->
                    homeData.list.onEach {
                        when(it){
                            is Video -> it.itemType = AppAdapter.Type.ITEM_VIDEO
                        }
                    }
                    homeData.itemSpacing = resources.getDimension(R.dimen.home_spacing).toInt()
                    homeData.itemType = AppAdapter.Type.ITEM_HOME
                    add(homeData)
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