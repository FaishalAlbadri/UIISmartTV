package com.faishalbadri.uiismarttv.adapter.viewholder

import android.util.Log
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.dummy.Video
import com.faishalbadri.uiismarttv.databinding.ItemVideoBinding
import com.faishalbadri.uiismarttv.fragment.home.HomeFragment
import com.faishalbadri.uiismarttv.fragment.radio.RadioFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class VideoViewHolder (
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var video: Video

    fun bind(video: Video) {
        this.video = video

        when (_binding) {
            is ItemVideoBinding -> displayItem(_binding)

        }
    }

    private fun displayItem(binding: ItemVideoBinding) {
        binding.root.apply {
            setOnFocusChangeListener { _, hasFocus ->
                val animation = when {
                    hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                    else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                }
                binding.root.startAnimation(animation)
                animation.fillAfter = true
            }

            setOnClickListener {
                when (val fragment = context.toActivity()?.getCurrentFragment()) {
                    is HomeFragment -> Log.i("","")
                }
            }
        }

        binding.apply {
            Glide.with(context)
                .load(video.img)
                .into(imgVideo)

            txtTitle.text = video.title
            txtDesc.text = video.desc
        }
    }
}