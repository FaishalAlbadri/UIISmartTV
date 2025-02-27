package com.faishalbadri.uiismarttv.adapter.viewholder

import android.graphics.PorterDuff
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.local.Video
import com.faishalbadri.uiismarttv.databinding.ItemVideoBinding
import com.faishalbadri.uiismarttv.databinding.ItemVideoVerticalBinding
import com.faishalbadri.uiismarttv.fragment.home.HomeFragment
import com.faishalbadri.uiismarttv.fragment.home.HomeFragmentDirections
import com.faishalbadri.uiismarttv.fragment.search.SearchFragment
import com.faishalbadri.uiismarttv.fragment.search.SearchFragmentDirections
import com.faishalbadri.uiismarttv.fragment.video.VideoFragment
import com.faishalbadri.uiismarttv.fragment.video.VideoFragmentDirections
import com.faishalbadri.uiismarttv.fragment.video.VideoPlayerFragment
import com.faishalbadri.uiismarttv.utils.clickWithDebounce
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class VideoViewHolder(
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
            is ItemVideoVerticalBinding -> displayItemVertical(_binding)

        }
    }

    private fun displayItemVertical(binding: ItemVideoVerticalBinding) {
        binding.apply {
            imgVideo.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    val animation = when {
                        hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                        else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                    }
                    startAnimation(animation)
                    animation.fillAfter = true

                    when {
                        hasFocus() -> txtTitle.setTextColor(
                            context.resources.getColor(
                                R.color.black,
                                null
                            )
                        )

                        else -> txtTitle.setTextColor(
                            context.resources.getColor(
                                R.color.black_50,
                                null
                            )
                        )
                    }
                }
                clickWithDebounce {
                    when (context.toActivity()?.getCurrentFragment()) {
                        is VideoFragment -> findNavController().navigate(
                            VideoFragmentDirections.actionVideoToPlayer(
                                video.id,
                                video.title,
                                video.desc
                            )
                        )
                    }
                }
            }

            Glide.with(context)
                .load(video.img)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(imgVideo)

            txtTitle.text = video.title
            txtDate.text = video.date
        }
    }

    private fun displayItem(binding: ItemVideoBinding) {
        if (video.id.equals("")) {
            binding.apply {
                imgVideo.apply {
                    setFocusable(false)
                    setFocusableInTouchMode(false)
                    setBackground(null)
                    visibility = View.INVISIBLE
                }

                txtTitle.visibility = View.INVISIBLE

                root.apply {
                    setFocusable(true)
                    setFocusableInTouchMode(true)

                    setOnFocusChangeListener { _, hasFocus ->
                        val animation = when {
                            hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                            else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                        }
                        startAnimation(animation)
                        animation.fillAfter = true

                        when {
                            hasFocus() -> {
                                isMore.apply {
                                    imgMore.apply {
                                        setColorFilter(
                                            ContextCompat.getColor(context, R.color.primary_blue),
                                            PorterDuff.Mode.SRC_IN
                                        )
                                    }
                                    txtMore.apply {
                                        setTextColor(
                                            context.resources.getColor(
                                                R.color.primary_blue,
                                                null
                                            )
                                        )
                                    }
                                }
                            }

                            else -> {
                                isMore.apply {
                                    imgMore.apply {
                                        setColorFilter(
                                            ContextCompat.getColor(context, R.color.black),
                                            PorterDuff.Mode.SRC_IN
                                        )
                                    }
                                    txtMore.apply {
                                        setTextColor(
                                            context.resources.getColor(
                                                R.color.black,
                                                null
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    clickWithDebounce {
                        when (context.toActivity()?.getCurrentFragment()) {
                            is HomeFragment -> findNavController().navigate(HomeFragmentDirections.actionHomeToVideo())
                        }
                    }
                }

                isMore.apply {
                    root.apply {
                        visibility = View.VISIBLE
                    }
                    txtMore.text = "Lihat Semua Video"
                }

            }
        } else {
            binding.apply {
                imgVideo.apply {
                    setOnFocusChangeListener { _, hasFocus ->
                        val animation = when {
                            hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                            else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                        }
                        startAnimation(animation)
                        animation.fillAfter = true

                        when {
                            hasFocus() -> txtTitle.setTextColor(
                                context.resources.getColor(
                                    R.color.black,
                                    null
                                )
                            )

                            else -> txtTitle.setTextColor(
                                context.resources.getColor(
                                    R.color.black_50,
                                    null
                                )
                            )
                        }
                    }
                    clickWithDebounce {
                        when (context.toActivity()?.getCurrentFragment()) {
                            is HomeFragment -> findNavController().navigate(
                                HomeFragmentDirections.actionHomeToPlayer(
                                    video.id,
                                    video.title,
                                    video.desc
                                )
                            )

                            is SearchFragment -> findNavController().navigate(
                                SearchFragmentDirections.actionSearchToPlayer(
                                    video.id,
                                    video.title,
                                    video.desc
                                )
                            )
                        }
                    }
                }

                Glide.with(context)
                    .load(video.img)
                    .transform(CenterCrop(), RoundedCorners(4))
                    .into(imgVideo)

                txtTitle.text = video.title
                txtDesc.text = video.date
            }
        }
    }
}