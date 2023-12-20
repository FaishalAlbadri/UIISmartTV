package com.faishalbadri.uiismarttv.adapter.viewholder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.databinding.ItemAdzanBinding
import com.faishalbadri.uiismarttv.utils.capitalizeWords

class AdzanViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var adzan: Adzan

    fun bind(adzan: Adzan) {
        this.adzan = adzan

        when (_binding) {
            is ItemAdzanBinding -> displayItem(_binding)
        }
    }

    private fun displayItem(binding: ItemAdzanBinding) {
        binding.apply {

            root.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    val animation = when {
                        hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                        else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                    }
                    binding.root.startAnimation(animation)
                    animation.fillAfter = true
                }
            }

            txtSholat.text = adzan.id.capitalizeWords()
            txtWaktu.text = adzan.value
        }
    }
}