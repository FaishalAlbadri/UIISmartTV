package com.faishalbadri.uiismarttv.adapter.viewholder

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.local.RadioData
import com.faishalbadri.uiismarttv.databinding.ItemRadioBinding
import com.faishalbadri.uiismarttv.fragment.radio.RadioFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class RadioViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var radio: RadioData

    fun bind(radio: RadioData) {
        this.radio = radio
        when (_binding) {
            is ItemRadioBinding -> displayItem(_binding)
        }
    }

    private fun displayItem(binding: ItemRadioBinding) {
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
                    is RadioFragment -> fragment.playRadio(radio)
                }
            }
        }
        binding.apply {
            Glide.with(context)
                .load(radio.imageRadio)
                .transform(CenterCrop(), GranularRoundedCorners(4F, 4F, 0F, 0F))
                .into(imgRadio)
            txtRadio.text = radio.namaRadio
            txtRadioSignal.text = radio.signalRadio
        }
    }
}