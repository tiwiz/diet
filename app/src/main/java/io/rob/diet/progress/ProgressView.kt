package io.rob.diet.progress

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import io.rob.diet.databinding.ProgressRecapElementBinding

class ProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding  = ProgressRecapElementBinding.inflate(LayoutInflater.from(context), this, true)

    fun bind(recapUI: RecapUI) {
        with(binding) {
            weightStart.setText(recapUI.weightStart)
            weightLast.setText(recapUI.weightEnd)
            weightDelta.setText(recapUI.weightDelta)

            bmiStart.setText(recapUI.bmiStart)
            bmiLast.setText(recapUI.bmiEnd)
            bmiDelta.setText(recapUI.bmiDelta)

            bodyFatStart.setText(recapUI.bodyFatPctStart)
            bodyFatLast.setText(recapUI.bodyFatPctEnd)
            bodyFatDelta.setText(recapUI.bodyFatPctDelta)

            waistStart.setText(recapUI.waistStart)
            waistLast.setText(recapUI.waistEnd)
            waistDelta.setText(recapUI.waistDelta)

            umbilicalStart.setText(recapUI.umbilicalStart)
            umbilicalLast.setText(recapUI.umbilicalEnd)
            umbilicalDelta.setText(recapUI.umbilicalDelta)

            hipStart.setText(recapUI.hipStart)
            hipLast.setText(recapUI.hipEnd)
            hipDelta.setText(recapUI.hipDelta)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun TextView.setText(value: Float) {
        text = "%.2f".format(value)
    }
}