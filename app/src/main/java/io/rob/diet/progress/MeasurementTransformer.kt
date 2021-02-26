package io.rob.diet.progress

import io.rob.diet.R
import javax.inject.Inject


class MeasurementTransformer @Inject constructor(){

    fun toRecapUI(measurements: List<Measurement>): RecapUI? {

        if (measurements.isEmpty()) {
            return null
        }

        val (first, last) = measurements.firstAndLastOrNull()

        return RecapUI(
            weightStart = first.weight,
            bmiStart = first.bmi,
            waistStart = first.waist,
            umbilicalStart = first.umbilical,
            hipStart = first.hip,
            bodyFatPctStart = first.bodyFatPct,
            weightEnd = last.weight,
            bmiEnd = last.bmi,
            waistEnd = last.waist,
            umbilicalEnd = last.umbilical,
            hipEnd = last.hip,
            bodyFatPctEnd = last.bodyFatPct,
            weightDelta = last.weight - first.weight,
            bmiDelta = last.bmi - first.bmi,
            waistDelta = last.waist - first.waist,
            umbilicalDelta = last.umbilical - first.umbilical,
            hipDelta = last.hip - first.hip,
            bodyFatPctDelta = last.bodyFatPct - first.bodyFatPct
        )
    }

    private fun List<Measurement>.firstAndLastOrNull() : Pair<Measurement, Measurement> {
        val sorted = sortedBy { it.date }

        return sorted.first() to sorted.last()
    }

    fun toComposeRecapUI(measurements: List<Measurement>): ComposeRecapUI? {

        if (measurements.isEmpty()) {
            return emptyMap()
        }

        val (first, last) = measurements.firstAndLastOrNull()

        return hashMapOf(
            R.string.weight_hint to RecapElement(start = first.weight, end = last.weight),
            R.string.bmi to RecapElement(start = first.bmi, end = last.bmi),
            R.string.waist to RecapElement(start = first.waist, end = last.waist),
            R.string.umbilical to RecapElement(start = first.umbilical, end = last.umbilical),
            R.string.hip to RecapElement(start = first.hip, end = last.hip),
            R.string.body_fat to RecapElement(start = first.bodyFatPct, end = last.bodyFatPct)
        )
    }
}