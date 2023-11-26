package com.faishalbadri.uiismarttv

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.faishalbadri.navigation.setupWithNavController
import com.faishalbadri.uiismarttv.databinding.ActivityHomeBinding
import com.faishalbadri.uiismarttv.databinding.ContentHeaderMenuMainBinding
import com.faishalbadri.uiismarttv.fragment.profile.ProfileFragment
import com.faishalbadri.uiismarttv.utils.getCurrentFragment

class HomeActivity : FragmentActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = this.supportFragmentManager
            .findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.home)

        binding.navMain.setupWithNavController(navController)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.navMainFragment.isFocusedByDefault = true
        }

        navController.addOnDestinationChangedListener {_, destination, _ ->
            binding.navMain.headerView?.apply {
                val header = ContentHeaderMenuMainBinding.bind(this)

                Glide.with(context)
                    .load(resources.getIdentifier("logo_uii", "drawable", this@HomeActivity.packageName))
                    .into(header.ivNavigationHeaderIcon)

                header.tvNavigationHeaderTitle.text = getString(
                    R.string.app_name
                )

                setOnOpenListener {
                    header.tvNavigationHeaderTitle.visibility = View.VISIBLE
                }
                setOnCloseListener {
                    header.tvNavigationHeaderTitle.visibility = View.GONE
                }

                setOnClickListener {
                    navController.navigate(NavMainGraphDirections.actionGlobalProfile())
                }
            }

            when (destination.id) {
                R.id.search,
                R.id.home,
                R.id.radio,
                R.id.gallery -> binding.navMain.visibility = View.VISIBLE
                else -> binding.navMain.visibility = View.GONE
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when (navController.currentDestination?.id) {
                    R.id.home -> when {
                        binding.navMain.hasFocus() -> finish()
                        else -> binding.navMain.requestFocus()
                    }
                    R.id.search,
                    R.id.radio,
                    R.id.gallery -> when {
                        binding.navMain.hasFocus() -> binding.navMain.findViewById<View>(R.id.home)
                            .let {
                                it.requestFocus()
                                it.performClick()
                            }
                        else -> binding.navMain.requestFocus()
                    }
                    else -> {
                        when (val currentFragment = getCurrentFragment()) {
                            is ProfileFragment -> currentFragment.onBackPressed()
                            else -> false
                        }.takeIf { !it }?.let {
                            navController.navigateUp()
                        }
                    }
                }
            }
        })
    }
}