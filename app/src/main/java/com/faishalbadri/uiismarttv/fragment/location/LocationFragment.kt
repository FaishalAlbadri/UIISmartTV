package com.faishalbadri.uiismarttv.fragment.location

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.adapter.AppAdapter
import com.faishalbadri.uiismarttv.data.local.Adzan
import com.faishalbadri.uiismarttv.data.local.LocationDataPreferences
import com.faishalbadri.uiismarttv.databinding.FragmentLocationBinding
import com.faishalbadri.uiismarttv.utils.ViewModelFactory
import com.faishalbadri.uiismarttv.utils.toActivity

class LocationFragment : Fragment() {

    private var _binding: FragmentLocationBinding? = null
    val binding get() = _binding!!

    lateinit var viewModelFactory: ViewModelFactory
    val viewModel: LocationViewModel by viewModels { viewModelFactory }
    private val appAdapter = AppAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        viewModelFactory = ViewModelFactory.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLocation().observe(viewLifecycleOwner) {
            if (it.id.isEmpty()) {
                setView()
            }
        }
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                LocationViewModel.State.Loading -> showLoading(true)
                is LocationViewModel.State.SuccessLoadLocation -> {
                    showLoading(false)
                    loadData(state.data)
                }

                is LocationViewModel.State.FailedLoadLocation -> {
                    showLoading(false)
                    Toast.makeText(
                        requireContext(),
                        state.error.message ?: "",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

    fun saveLocation(location: Adzan) {
        viewModel.saveSession(
            LocationDataPreferences(
                id = location.id,
                nama = location.value
            )
        )
        requireContext().toActivity()?.apply {
            finish()
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
    }

    private fun setView() {
        if (appAdapter.items.size == 0) {
            viewModel.getLocationAPI()
        }
        binding.vgvLocation.apply {
            adapter = appAdapter
            setItemSpacing(resources.getDimension(R.dimen.home_spacing).toInt() * 2)
        }
        binding.root.requestFocus()
    }

    private fun loadData(dataProvinsi: List<Adzan>) {
        binding.txtTitle.visibility = View.VISIBLE
        appAdapter.submitList(dataProvinsi.onEach {
            it.itemType = AppAdapter.Type.ITEM_LOCATION
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}