package com.faishalbadri.uiismarttv.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.local.News
import com.faishalbadri.uiismarttv.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    val binding get() = _binding!!

    private val args by navArgs<NewsFragmentArgs>()
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
        setView()
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                NewsViewModel.State.Loading -> showLoading(true)
                NewsViewModel.State.LoadingMore -> appAdapter.isLoading = true
                is NewsViewModel.State.SuccessLoadNews -> {
                    showLoading(false)
                    appAdapter.isLoading = false
                    loadData(state.data, state.hasMore)
                }
                is NewsViewModel.State.FailedLoadNews -> {
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
    }

    private fun setView() {
        activityHome = getActivity() as HomeActivity
        if (appAdapter.items.size == 0) {
            viewModel.getNews(args.category)
        }
        binding.txtTitle.text = args.category
        binding.vgvNews.apply {
            adapter = appAdapter
            setItemSpacing(resources.getDimension(R.dimen.home_spacing).toInt() * 2)
        }
        binding.root.requestFocus()
    }

    private fun loadData(news: List<News>, hasMore: Boolean) {
        appAdapter.submitList(news.onEach {
            it.itemType = AppAdapter.Type.ITEM_NEWS_VERTICAL
        })
        if (hasMore) {
            appAdapter.setOnLoadMoreListener {
                viewModel.loadMoreNews(args.category)
            }
        } else {
            appAdapter.setOnLoadMoreListener(null)
        }
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