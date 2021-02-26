package io.rob.diet.progress

import androidx.annotation.StringRes
import io.rob.diet.Charts

data class RecapUI(
    val weightStart: Float,
    val bmiStart: Float,
    val waistStart: Float,
    val umbilicalStart: Float,
    val hipStart: Float,
    val bodyFatPctStart: Float,
    val weightEnd: Float,
    val bmiEnd: Float,
    val waistEnd: Float,
    val umbilicalEnd: Float,
    val hipEnd: Float,
    val bodyFatPctEnd: Float,
    val weightDelta: Float,
    val bmiDelta: Float,
    val waistDelta: Float,
    val umbilicalDelta: Float,
    val hipDelta: Float,
    val bodyFatPctDelta: Float
)

data class RecapElement(
    @StringRes val titleRes: Int,
    val start: Float,
    val end: Float
) {
    val delta = end - start
}

typealias ComposeRecapUI = Map<Charts, RecapElement>

class EmptyRecapException : IllegalArgumentException()