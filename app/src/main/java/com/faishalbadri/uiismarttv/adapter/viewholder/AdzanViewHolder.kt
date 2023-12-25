package com.faishalbadri.uiismarttv.adapter.viewholder

import android.os.Build
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.databinding.ItemAdzanBinding
import com.faishalbadri.uiismarttv.fragment.home.HomeFragment
import com.faishalbadri.uiismarttv.utils.capitalizeWords
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class AdzanViewHolder(
    private val _binding: ViewBinding
) : RecyclerView.ViewHolder(
    _binding.root
) {

    private val context = itemView.context
    private lateinit var adzan: Adzan
    private var isActiveLaunch = true

    fun bind(adzan: Adzan) {
        this.adzan = adzan

        when (_binding) {
            is ItemAdzanBinding -> displayItem(_binding)
        }
    }

    private fun displayItem(binding: ItemAdzanBinding) {
        binding.apply {
            imgSholat.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    val animation = when {
                        hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                        else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                    }
                    binding.root.startAnimation(animation)
                    animation.fillAfter = true
                }
            }

            Glide.with(context)
                .load(R.drawable.adzan)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(imgSholat)

            txtSholat.text = adzan.id.capitalizeWords()
        }

        CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                if (isActiveLaunch) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val waktuAdzan = adzan.value.split(":").toTypedArray()
                        val waktuSaatIni = LocalTime.now()
                        val waktuTarget =
                            LocalTime.of(waktuAdzan[0].toInt(), waktuAdzan[1].toInt(), 0)
                        val selisihWaktu = waktuSaatIni.until(waktuTarget, ChronoUnit.SECONDS)
                        val jam = formatwaktu(selisihWaktu / 3600)
                        val sisaDetik = selisihWaktu % 3600
                        val menit = formatwaktu(sisaDetik / 60)
                        val detik = formatwaktu(sisaDetik % 60)

                        if (selisihWaktu <= 0L) {
                            binding.txtWaktu.text = adzan.value
                            if (selisihWaktu == 0L) {
                                when (val fragment = context.toActivity()?.getCurrentFragment()) {
                                    is HomeFragment -> fragment.activityHome.initAdzan()
                                }
                            }
                            isActiveLaunch = false
                        } else {
                            binding.txtWaktu.text =
                                adzan.value + " (-" + jam + ":" + menit + ":" + detik + ")"
                        }
                        delay(1000L)
                    } else {
                        isActiveLaunch = false
                    }
                } else {
                    this.cancel()
                }
            }
        }
    }

    fun formatwaktu(long: Long): String {
        var s = long.toString()
        if (s.length < 2) {
            s = "0" + s
        }
        return s
    }
}