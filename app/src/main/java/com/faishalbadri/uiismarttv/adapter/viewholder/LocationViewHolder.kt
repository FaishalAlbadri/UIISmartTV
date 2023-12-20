package com.faishalbadri.uiismarttv.adapter.viewholder

import android.graphics.drawable.GradientDrawable
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.databinding.ItemLocationBinding
import com.faishalbadri.uiismarttv.fragment.location.LocationFragment
import com.faishalbadri.uiismarttv.fragment.location.LocationFragmentDirections
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

class LocationViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var adzan: Adzan

    fun bind(adzan: Adzan) {
        this.adzan = adzan

        when (_binding) {
            is ItemLocationBinding -> displayItem(_binding)
        }
    }

    private fun displayItem(binding: ItemLocationBinding) {
        binding.root.apply {
            val colors = context.resources.getIntArray(R.array.location)
            (background as? GradientDrawable)?.setColor(colors[bindingAdapterPosition % colors.size])

            setOnClickListener {
                when (val fragment = context.toActivity()?.getCurrentFragment()) {
                    is LocationFragment -> {
                        if (fragment.args.provinsi.equals("0")) {
                            findNavController().navigate(
                                LocationFragmentDirections.actionLocationSelf(
                                    adzan.value
                                )
                            )
                        } else {
                            fragment.saveLocation(adzan.value)
                        }
                    }
                }
            }
            setOnFocusChangeListener { _, hasFocus ->
                val animation = when {
                    hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                    else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                }
                binding.root.startAnimation(animation)
                animation.fillAfter = true
            }
        }

        binding.txtLocation.text = adzan.value
    }
}