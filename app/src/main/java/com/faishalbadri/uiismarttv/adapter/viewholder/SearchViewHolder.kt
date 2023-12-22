package com.faishalbadri.uiismarttv.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.local.SearchData
import com.faishalbadri.uiismarttv.databinding.ItemSearchBinding

class SearchViewHolder (
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {
    private val context = itemView.context
    private lateinit var search: SearchData

    val childRecyclerView: RecyclerView?
        get() = when (_binding) {
            is ItemSearchBinding -> _binding.vgvSearch
            else -> null
        }

    fun bind(search: SearchData) {
        this.search = search

        when (_binding) {
            is ItemSearchBinding -> displayItemSearch(_binding)
        }
    }

    private fun displayItemSearch(binding: ItemSearchBinding) {
        binding.apply {
            txtTitle.text = search.msg

            vgvSearch.apply {
                adapter = AppAdapter().apply {
                    items.addAll(search.list)
                }
                setItemSpacing(search.itemSpacing)
            }
        }
    }
}