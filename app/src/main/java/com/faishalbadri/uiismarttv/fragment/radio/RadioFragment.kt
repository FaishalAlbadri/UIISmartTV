package com.faishalbadri.uiismarttv.fragment.radio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.dummy.RadioData
import com.faishalbadri.uiismarttv.databinding.FragmentRadioBinding

class RadioFragment : Fragment() {

    private var _binding: FragmentRadioBinding? = null
    val binding get() = _binding!!

    private val viewModel by viewModels<RadioViewModel>()
    private val appAdapter = AppAdapter()

    private lateinit var activityHome: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRadioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.radioData.observe(viewLifecycleOwner) {
            loadData(it)
        }
        setView()
    }

    private fun setView() {
        binding.vgRadio.apply {
            adapter = appAdapter
            setItemSpacing(requireContext().resources.getDimension(R.dimen.radio_spacing).toInt())
        }

        viewModel.getRadio()
        binding.root.requestFocus()
        activityHome = getActivity() as HomeActivity
    }

    private fun setBottomBar() {
        if (activityHome.player != null && activityHome.dataRadio != null) {
            binding.apply {
                cvRadio.txtRadioName.text = activityHome.dataRadio!!.namaRadio
                cvRadio.txtRadioSignal.text = activityHome.dataRadio!!.signalRadio
                Glide.with(activity?.applicationContext!!)
                    .load(R.drawable.ic_pause_circle)
                    .into(cvRadio.imgRadioStatus)
            }
        }
    }

    private fun loadData(data: MutableList<RadioData>) {
        appAdapter.submitList(data.onEach {
            it.itemType = AppAdapter.Type.RADIO
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    fun playRadio(data: RadioData) {
        if (activityHome.player == null) {
            initRadio(data)
        } else {
            setStateStop()
            activityHome.releasePlayer()
            if (activityHome.dataRadio!!.link != data.link) {
                initRadio(data)
            }
        }
    }

    private fun initRadio(data: RadioData) {
        activityHome.initRadio(data)
    }

    fun setStateStop() {
        binding.apply {
            cvRadio.txtRadioName.text = ""
            cvRadio.txtRadioSignal.text = ""
            Glide.with(activity?.applicationContext!!)
                .load(R.drawable.ic_play_circle)
                .into(cvRadio.imgRadioStatus)
        }
    }

    fun setStateReady(data: RadioData) {
        binding.apply {
            cvRadio.txtRadioName.text = data.namaRadio
            cvRadio.txtRadioSignal.text = data.signalRadio
            Glide.with(activity?.applicationContext!!)
                .load(R.drawable.ic_pause_circle)
                .into(cvRadio.imgRadioStatus)
        }
    }

    fun setStateBuffering() {
        binding.apply {
            cvRadio.txtRadioName.text = "Loading Radio"
            cvRadio.txtRadioSignal.text = ""
            Glide.with(activity?.applicationContext!!)
                .load(R.raw.loading)
                .into(cvRadio.imgRadioStatus)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setBottomBar()
    }
}
