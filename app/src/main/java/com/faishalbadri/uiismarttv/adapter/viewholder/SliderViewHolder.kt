package com.faishalbadri.uiismarttv.adapter.viewholder

import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.dummy.Banner
import com.faishalbadri.uiismarttv.data.dummy.BannerResponse
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.data.dummy.Show
import com.faishalbadri.uiismarttv.data.dummy.Video
import com.faishalbadri.uiismarttv.databinding.ContentSliderBinding
import com.faishalbadri.uiismarttv.fragment.home.HomeFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class SliderViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var banner: BannerResponse

    fun bind(banner: BannerResponse) {
        this.banner = banner

        when (_binding) {
            is ContentSliderBinding -> displaySlider(_binding)
        }
    }

    private fun updateBackground(imageView: ImageView, uri: String){
        Glide.with(context)
            .load(uri)
            .into(imageView)
    }

    private fun displaySlider(binding: ContentSliderBinding) {
        val selected = banner.list.getOrNull(banner.selectedIndex) as? Show ?: return

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
            repeat(banner.list.size) { index ->
                val view = View(context).apply {
                    layoutParams = LinearLayout.LayoutParams(15, 15).apply {
                        setMargins(10, 0, 10, 0)
                    }
                    setBackgroundResource(R.drawable.bg_dot_indicator)
                    isSelected = (banner.selectedIndex == index)
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
                            banner.selectedIndex =
                                (banner.selectedIndex + 1) % banner.list.size

                            when (val fragment = context.toActivity()?.getCurrentFragment()) {
                                is HomeFragment -> when (val it =
                                    banner.list[banner.selectedIndex]) {
                                    is Banner -> updateBackground(binding.imgHomeBackground, it.img)
                                    is News -> updateBackground(binding.imgHomeBackground, it.img)
                                    is Video -> updateBackground(binding.imgHomeBackground, it.img)
                                }
                            }
                            bindingAdapter?.notifyItemChanged(bindingAdapterPosition)
                            return@setOnKeyListener true
                        }

                        KeyEvent.KEYCODE_DPAD_LEFT -> {
                            banner.selectedIndex =
                                (banner.selectedIndex - 1) % banner.list.size

                            if (banner.selectedIndex >= 0) {
                                when (val fragment = context.toActivity()?.getCurrentFragment()) {
                                    is HomeFragment -> when (val it =
                                        banner.list[banner.selectedIndex]) {
                                        is Banner -> updateBackground(binding.imgHomeBackground, it.img)
                                        is News -> updateBackground(binding.imgHomeBackground, it.img)
                                        is Video -> updateBackground(binding.imgHomeBackground, it.img)
                                    }
                                }
                                bindingAdapter?.notifyItemChanged(bindingAdapterPosition)
                            } else {
                                banner.selectedIndex =
                                    (banner.selectedIndex + 1) % banner.list.size
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