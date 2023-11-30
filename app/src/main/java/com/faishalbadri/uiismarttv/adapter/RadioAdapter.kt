package com.faishalbadri.uiismarttv.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.RadioData
import com.faishalbadri.uiismarttv.databinding.ItemRadioBinding
import com.faishalbadri.uiismarttv.fragment.radio.RadioFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment
import com.faishalbadri.uiismarttv.utils.toActivity

@UnstableApi
class RadioAdapter(radioData: MutableList<RadioData>) : RecyclerView.Adapter<RadioAdapter.MyViewHolder>() {

    private var radioData = radioData

    class MyViewHolder(private val binding: ItemRadioBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: RadioData){
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
                            is RadioFragment -> fragment.playRadio(data.link, data.namaRadio)
                        }
                    }
                }
                binding.apply {
                    Glide.with(itemView.context)
                        .load(data.imageRadio)
                        .centerCrop()
                        .into(imgRadio)
                    txtRadio.text = data.namaRadio
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRadioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return radioData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = radioData.get(position)
        if (data != null) {
            holder.bind(data)
        }
    }
}