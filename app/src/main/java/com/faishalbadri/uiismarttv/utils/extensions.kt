package com.faishalbadri.uiismarttv.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.faishalbadri.uiismarttv.HomeActivity
import com.faishalbadri.uiismarttv.R

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Context.toActivity(): FragmentActivity? = this as? FragmentActivity

fun FragmentActivity.getCurrentFragment(): Fragment? = when (this) {
    is HomeActivity -> {
        val navHostFragment = this.supportFragmentManager
            .findFragmentById(R.id.nav_main_fragment) as NavHostFragment
        navHostFragment.childFragmentManager.fragments.firstOrNull()
    }
    else -> null
}