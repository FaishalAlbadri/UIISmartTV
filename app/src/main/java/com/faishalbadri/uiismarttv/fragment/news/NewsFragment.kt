package com.faishalbadri.uiismarttv.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<NewsViewModel>()
    private val appAdapter = AppAdapter()
    private lateinit var activityHome: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.newsData.observe(viewLifecycleOwner) {
            loadData(it)
        }
        setView()
    }

    private fun setView() {
        activityHome = getActivity() as HomeActivity
        binding.vgvNews.apply {
            adapter = appAdapter
            setItemSpacing(resources.getDimension(R.dimen.home_spacing).toInt() * 2)
        }
        viewModel.getNews()
        binding.root.requestFocus()
    }

    private fun loadData(news: List<News>) {
        appAdapter.submitList(news.onEach {
            it.itemType = AppAdapter.Type.ITEM_NEWS_VERTICAL
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