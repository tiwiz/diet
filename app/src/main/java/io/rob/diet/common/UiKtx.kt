package io.rob.diet.common

import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import io.rob.diet.MainActivity

fun Fragment.updateExternalColors(@ColorInt statusbarColor : Int, @ColorRes bottomBarColor : Int) {
    requireActivity().window.run {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = statusbarColor
    }

    (requireActivity() as? MainActivity)?.updateNavigationBarColorTo(bottomBarColor)
}

fun Fragment.updateExternalColors(@ColorRes mainColor : Int) {
    val color = ContextCompat.getColor(requireContext(), mainColor)

    updateExternalColors(color, mainColor)
}

fun TextInputLayout.validOrSetError(errorMessage: String): Boolean =
    if (editText?.text.toString().isNotBlank()) {
        true
    } else {
        error = errorMessage
        false
    }

fun TextInputLayout.setOnFocusBehaviour() {
    setOnFocusChangeListener { _, _ ->
        error = null
    }
}
