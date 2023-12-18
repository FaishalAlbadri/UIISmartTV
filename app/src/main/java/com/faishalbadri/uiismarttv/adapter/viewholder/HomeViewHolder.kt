package com.faishalbadri.uiismarttv.adapter.viewholder

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.HomeData
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.Show
import com.faishalbadri.uiismarttv.data.dummy.Video
import com.faishalbadri.uiismarttv.databinding.ContentSliderBinding
import com.faishalbadri.uiismarttv.databinding.ItemHomeBinding
import com.faishalbadri.uiismarttv.fragment.home.HomeFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class HomeViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var home: HomeData

    val childRecyclerView: RecyclerView?
        get() = when (_binding) {
            is ItemHomeBinding -> _binding.hgvHome
            else -> null
        }

    fun bind(home: HomeData) {
        this.home = home

        when (_binding) {
            is ItemHomeBinding -> displayItemHome(_binding)

            is ContentSliderBinding -> displaySlider(_binding)
        }
    }

    private fun displayItemHome(binding: ItemHomeBinding) {
        binding.apply {
            txtTitle.text = home.msg

            hgvHome.apply {
                setRowHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                adapter = AppAdapter().apply {
                    items.addAll(home.list)
                }
                setItemSpacing(home.itemSpacing)
            }
        }
    }

    private fun updateBackground(imageView: ImageView, uri: String) {
        Glide.with(context)
            .load(uri)
            .into(imageView)
        Log.i("imagebanner", uri)
    }

    private fun displaySlider(binding: ContentSliderBinding) {
        val selected = home.list.getOrNull(home.selectedIndex) as? Show ?: return

        binding.txtSliderTitle.text = when (selected) {
            is Banner -> selected.title
            is News -> selected.title
            is Video -> selected.title
        }

        binding.txtSliderDesc.text = when (selected) {
            is Banner -> selected.desc
            is News -> selected.desc
            is Video -> selected.desc
        }

        binding.llDotsIndicator.apply {

            removeAllViews()
            repeat(home.list.size) { index ->
                val view = View(context).apply {
                    layoutParams = LinearLayout.LayoutParams(15, 15).apply {
                        setMargins(10, 0, 10, 0)
                    }
                    setBackgroundResource(R.drawable.bg_dot_indicator)
                    isSelected = (home.selectedIndex == index)
                }
                addView(view)
            }

            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    when (val fragment = context.toActivity()?.getCurrentFragment()) {
                        is HomeFragment -> when (selected) {
                            is Banner -> updateBackground(binding.imgHomeBackground, selected.img)
                            is News -> updateBackground(binding.imgHomeBackground, selected.img)
                            is Video -> updateBackground(binding.imgHomeBackground, selected.img)
                        }
                    }
                }
            }
            setOnKeyListener { _, _, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (event.keyCode) {
                        KeyEvent.KEYCODE_DPAD_RIGHT -> {
                            home.selectedIndex =
                                (home.selectedIndex + 1) % home.list.size

                            when (val fragment = context.toActivity()?.getCurrentFragment()) {
                                is HomeFragment -> when (val it =
                                    home.list[home.selectedIndex]) {
                                    is Banner -> updateBackground(binding.imgHomeBackground, it.img)
                                    is News -> updateBackground(binding.imgHomeBackground, it.img)
                                    is Video -> updateBackground(binding.imgHomeBackground, it.img)
                                }
                            }
                            bindingAdapter?.notifyItemChanged(bindingAdapterPosition)
                            return@setOnKeyListener true
                        }

                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            home.selectedIndex =
                                (home.selectedIndex - 1) % home.list.size

                            if (home.selectedIndex >= 0) {
                                when (val fragment = context.toActivity()?.getCurrentFragment()) {
                                    is HomeFragment -> when (val it =
                                        home.list[home.selectedIndex]) {
                                        is Banner -> updateBackground(
                                            binding.imgHomeBackground,
                                            it.img
                                        )

                                        is News -> updateBackground(
                                            binding.imgHomeBackground,
                                            it.img
                                        )

                                        is Video -> updateBackground(
                                            binding.imgHomeBackground,
                                            it.img
                                        )
                                    }
                                }
                                bindingAdapter?.notifyItemChanged(bindingAdapterPosition)
                            } else {
                                home.selectedIndex =
                                    (home.selectedIndex + 1) % home.list.size
                                when (val fragment = context.toActivity()?.getCurrentFragment()) {
                                    is HomeFragment -> fragment.toNavigate()
                                }
                            }
                            return@setOnKeyListener true
                        }
                    }
                }
                false
            }

        }
    }

}