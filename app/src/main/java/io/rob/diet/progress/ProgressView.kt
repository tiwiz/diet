package io.rob.diet.progress

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import io.rob.diet.databinding.ProgressRecapElementBinding

class ProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding  = ProgressRecapElementBinding.inflate(LayoutInflater.from(context), this, true)

}