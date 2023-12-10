package com.faishalbadri.uiismarttv.adapter.viewholder

import android.util.Log
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.databinding.ItemNewsBinding
import com.faishalbadri.uiismarttv.fragment.home.HomeFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class NewsViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var news: News

    fun bind(news: News) {
        this.news = news

        when (_binding) {
            is ItemNewsBinding -> displayItem(_binding)

        }
    }

    private fun displayItem(binding: ItemNewsBinding) {
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
                .load(news.img)
                .transform(CenterCrop(), GranularRoundedCorners(4F, 4F,0F,0F))
                .into(imgNews)

            txtTitle.text = news.title
            txtDesc.text = news.desc
        }
    }
}