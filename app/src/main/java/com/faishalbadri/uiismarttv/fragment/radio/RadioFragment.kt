package com.faishalbadri.uiismarttv.fragment.radio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.databinding.FragmentRadioBinding

class RadioFragment : Fragment() {

    private lateinit var binding: FragmentRadioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRadioBinding.inflate(inflater, container, false)

        return binding.root
    }
}