package com.faishalbadri.uiismarttv.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R

fun FragmentActivity.getCurrentFragment(): Fragment? = when (this) {
    is HomeActivity -> {
        val navHostFragment = this.supportFragmentManager
            .findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navHostFragment.childFragmentManager.fragments.firstOrNull()
    }
    else -> null
}