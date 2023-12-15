package com.faishalbadri.uiismarttv.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faishalbadri.uiismarttv.adapter.viewholder.LoadingViewHolder
import com.faishalbadri.uiismarttv.adapter.viewholder.HomeViewHolder
import com.faishalbadri.uiismarttv.adapter.viewholder.NewsViewHolder
import com.faishalbadri.uiismarttv.adapter.viewholder.RadioViewHolder
import com.faishalbadri.uiismarttv.adapter.viewholder.VideoViewHolder
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.RadioData
import com.faishalbadri.uiismarttv.data.dummy.Video
import com.faishalbadri.uiismarttv.databinding.ContentSliderBinding
import com.faishalbadri.uiismarttv.databinding.ItemHomeBinding
import com.faishalbadri.uiismarttv.databinding.ItemLoadingBinding
import com.faishalbadri.uiismarttv.databinding.ItemNewsBinding
import com.faishalbadri.uiismarttv.databinding.ItemNewsRecomendationBinding
import com.faishalbadri.uiismarttv.databinding.ItemNewsVerticalBinding
import com.faishalbadri.uiismarttv.databinding.ItemRadioBinding
import com.faishalbadri.uiismarttv.databinding.ItemVideoBinding
import com.faishalbadri.uiismarttv.databinding.ItemVideoVerticalBinding

class AppAdapter(
    val items: MutableList<Item> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Item {
        var itemType: Type
    }

    enum class Type {
        LOADING,
        HOME,
        RADIO,

        ITEM_HOME,
        ITEM_VIDEO,
        ITEM_VIDEO_VERTICAL,
        ITEM_NEWS,
        ITEM_NEWS_VERTICAL,
        ITEM_NEWS_RECOMMENDATION
    }

    private val states = mutableMapOf<Int, Parcelable?>()

    var isLoading = false
    private var onLoadMoreListener: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (Type.values()[viewType]) {
            Type.LOADING -> LoadingViewHolder(
                ItemLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )

            Type.HOME -> HomeViewHolder(
                ContentSliderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Type.RADIO -> RadioViewHolder(
                ItemRadioBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Type.ITEM_HOME -> HomeViewHolder(
                ItemHomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Type.ITEM_VIDEO -> VideoViewHolder(
                ItemVideoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Type.ITEM_VIDEO_VERTICAL -> VideoViewHolder(
                ItemVideoVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Type.ITEM_NEWS -> NewsViewHolder(
                ItemNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            Type.ITEM_NEWS_VERTICAL -> NewsViewHolder(
                ItemNewsVerticalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            Type.ITEM_NEWS_RECOMMENDATION -> NewsViewHolder(
                ItemNewsRecomendationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= itemCount - 5 && !isLoading) {
            onLoadMoreListener?.invoke()
            isLoading = true
        }

        when (holder) {
            is HomeViewHolder -> holder.bind(items[position] as HomeData)
            is VideoViewHolder -> holder.bind(items[position] as Video)
            is NewsViewHolder -> holder.bind(items[position] as News)
            is RadioViewHolder -> holder.bind(items[position] as RadioData)
        }
    }

    override fun getItemCount(): Int = items.size + when {
        onLoadMoreListener != null -> 1
        else -> 0
    }

    override fun getItemViewType(position: Int): Int = items.getOrNull(position)?.itemType?.ordinal
        ?: Type.LOADING.ordinal

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)

        states[holder.layoutPosition] = when (holder) {
            is HomeViewHolder -> holder.childRecyclerView?.layoutManager?.onSaveInstanceState()
            else -> null
        }
    }

    fun onSaveInstanceState(recyclerView: RecyclerView) {
        for (position in items.indices) {
            val holder = recyclerView.findViewHolderForAdapterPosition(position) ?: continue

            states[position] = when (holder) {
                is HomeViewHolder -> holder.childRecyclerView?.layoutManager?.onSaveInstanceState()
                else -> null
            }
        }
    }

    fun submitList(list: List<Item>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size

            override fun getNewListSize() = list.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] === list[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == list[newItemPosition]
            }
        })

        items.clear()
        items.addAll(list)
        result.dispatchUpdatesTo(this)
    }


    fun setOnLoadMoreListener(onLoadMoreListener: (() -> Unit)?) {
        this.onLoadMoreListener = onLoadMoreListener
        if (onLoadMoreListener == null) {
            notifyItemRemoved(items.size)
        }
    }

}