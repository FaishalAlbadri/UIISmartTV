package com.faishalbadri.navigation

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout

class NavigationSlideHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var openListener: (() -> Unit)? = null
    private var closeListener: (() -> Unit)? = null


    fun setOnOpenListener(onOpen: () -> Unit) {
        openListener = onOpen
    }

    fun setOnCloseListener(onClose: () -> Unit) {
        closeListener = onClose
    }


    fun open() {
        isFocusable = true
        isFocusableInTouchMode = true

        openListener?.invoke()
        Log.i("NavigationDebug", "openNavigation")
    }

    fun close() {
        isFocusable = false
        isFocusableInTouchMode = false

        closeListener?.invoke()
        Log.i("NavigationDebug", "closeNavigation")
    }
}