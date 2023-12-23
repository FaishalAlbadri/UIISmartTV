package com.faishalbadri.uiismarttv.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.data.local.HomeData
import com.faishalbadri.uiismarttv.data.local.News
import com.faishalbadri.uiismarttv.data.local.Video
import com.faishalbadri.uiismarttv.databinding.FragmentSearchBinding
import com.faishalbadri.uiismarttv.utils.hideKeyboard

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    private var appAdapter = AppAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeSearch()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                SearchViewModel.State.Loading -> showLoading(true)
                is SearchViewModel.State.SuccessLoading -> {
                    showLoading(false)
                    displayContent(state.data)
                }

                is SearchViewModel.State.FailedLoading -> {
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

    private fun displayContent(search: List<HomeData>) {
        appAdapter.items.apply {
            clear()

            search.filter { it.list.isNotEmpty() }
                .onEach { searchData ->
                    searchData.list.onEach { show ->
                        when (show) {
                            is Video -> show.itemType = AppAdapter.Type.ITEM_VIDEO
                            is News -> show.itemType = AppAdapter.Type.ITEM_NEWS_SEARCH
                            is Adzan -> show.itemType = AppAdapter.Type.ITEM_ADZAN
                        }
                    }
                    searchData.itemSpacing = resources.getDimension(R.dimen.home_spacing).toInt()
                    searchData.itemType = AppAdapter.Type.ITEM_HOME
                    add(searchData)
                }
        }
        appAdapter.notifyDataSetChanged()
    }

    private fun initializeSearch() {
        binding.etSearch.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        viewModel.getSearch(text.toString())
                        hideKeyboard()
                        true
                    }

                    else -> false
                }
            }
        }

        binding.btnSearchClear.setOnClickListener {
            binding.etSearch.setText("")
        }

        binding.vgvSearch.apply {
            adapter = appAdapter
            setItemSpacing(requireContext().resources.getDimension(R.dimen.search_spacing).toInt())
        }

        binding.root.requestFocus()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appAdapter.onSaveInstanceState(binding.vgvSearch)
        _binding = null
    }
}