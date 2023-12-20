package com.faishalbadri.uiismarttv.fragment.home

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
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.data.local.HomeData
import com.faishalbadri.uiismarttv.data.local.News
import com.faishalbadri.uiismarttv.data.local.Video
import com.faishalbadri.uiismarttv.databinding.FragmentHomeBinding
import com.faishalbadri.uiismarttv.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    lateinit var viewModelFactory: ViewModelFactory
    val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private val appAdapter = AppAdapter()
    private lateinit var activityHome: HomeActivity

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

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeViewModel.State.Loading -> showLoading(true)
                is HomeViewModel.State.SuccessLoading -> {
                    showLoading(false)
                    displayContent(state.data)
                }

                is HomeViewModel.State.FailedLoading -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        state.error.message ?: "",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initializeHome() {
        viewModelFactory = ViewModelFactory.getInstance(requireContext())
        activityHome = getActivity() as HomeActivity
        if (appAdapter.items.size == 0) {
            viewModel.getLocation().observe(viewLifecycleOwner) {
                viewModel.getHome(
                    it.provinsi,
                    it.kota
                )
            }
        }
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

            //Video & News Content
            home.filter { it.msg != HomeData.Banner && it.list.isNotEmpty() }
                .onEach { homeData ->
                    homeData.list.onEach { show ->
                        when (show) {
                            is Video -> show.itemType = AppAdapter.Type.ITEM_VIDEO
                            is News -> show.itemType = AppAdapter.Type.ITEM_NEWS
                            is Adzan -> show.itemType = AppAdapter.Type.ITEM_ADZAN
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
        activityHome.binding.navMain.requestFocus()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}