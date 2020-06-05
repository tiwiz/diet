package io.rob.diet.common

import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

fun Fragment.tintStatusBar(@ColorInt color: Int) {
    requireActivity().window.run {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = color
    }
}