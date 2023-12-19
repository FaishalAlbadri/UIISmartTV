package com.faishalbadri.uiismarttv.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R
import com.faishalbadri.uiismarttv.data.dummy.News
import com.faishalbadri.uiismarttv.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NewsDetailFragmentArgs>()
    private val viewModel by viewModels<NewsViewModel>()

    private lateinit var activityHome: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                NewsViewModel.State.Loading -> showLoading(true)
                is NewsViewModel.State.SuccessLoadDetailNews -> {
                    showLoading(false)
                    loadData(state.data)
                }
                is NewsViewModel.State.FailedLoadDetailNews -> {
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
        setView()
    }

    private fun setView() {
        activityHome = getActivity() as HomeActivity
        binding.apply {
            btnTextToSpeech.apply {
                requestFocus()
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_play_circle,
                        null
                    )
                )
                setOnFocusChangeListener { v, hasFocus ->
                    val animation = when {
                        hasFocus -> AnimationUtils.loadAnimation(context, R.anim.zoom_in)
                        else -> AnimationUtils.loadAnimation(context, R.anim.zoom_out)
                    }
                    startAnimation(animation)
                    animation.fillAfter = true

                    when {
                        hasFocus() -> {
                            setColorFilter(
                                ContextCompat.getColor(context, R.color.primary_blue),
                                android.graphics.PorterDuff.Mode.SRC_IN
                            );
                            txtTts.setTextColor(
                                context.resources.getColor(
                                    R.color.primary_blue,
                                    null
                                )
                            )
                        }

                        else -> {
                            setColorFilter(
                                ContextCompat.getColor(context, R.color.black_50),
                                android.graphics.PorterDuff.Mode.SRC_IN
                            );
                            txtTts.setTextColor(
                                context.resources.getColor(
                                    R.color.black_50,
                                    null
                                )
                            )
                        }
                    }
                }

                setOnClickListener {
                    if (tag.toString() == "play") {
                        if (activityHome.textToSpeechStatus) {
                            setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.ic_stop_circle_outline,
                                    null
                                )
                            )
                            tag = "stop"
                            activityHome.initTextToSpeech(txtDesc.text.toString())
                        } else {
                            Toast.makeText(activity, "Tidak dapat memutar audio", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_play_circle,
                                null
                            )
                        )
                        tag = "play"
                        activityHome.stopTextToSpeech()
                    }
                }
            }

            nsvNews.apply {
                setOnFocusChangeListener { v, hasFocus ->
                    when {
                        hasFocus() -> {
                            txtDesc.setTextColor(
                                context.resources.getColor(
                                    R.color.black,
                                    null
                                )
                            )
                        }

                        else -> {
                            txtDesc.setTextColor(
                                context.resources.getColor(
                                    R.color.black_50,
                                    null
                                )
                            )
                        }
                    }
                }
            }
        }

        viewModel.getDetailNews(args.id)
    }

    private fun loadData(dataNews: News) {
        binding.apply {
            Glide.with(imgNews.context)
                .load(dataNews.img)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(imgNews)
            txtTitle.text = dataNews.title
            txtDesc.text = dataNews.desc
            txtDate.text = dataNews.date
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.isLoading.root.visibility =
            View.VISIBLE else binding.isLoading.root.visibility = View.GONE
    }

    fun setButtonPlayTextToSpeech() {
        binding.btnTextToSpeech.apply {
            setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_play_circle,
                    null
                )
            )
            tag = "play"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activityHome.stopTextToSpeech()
    }
}