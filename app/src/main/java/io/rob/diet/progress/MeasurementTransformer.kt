package io.rob.diet.progress

import javax.inject.Inject


class MeasurementTransformer @Inject constructor(){

    fun toRecapUI(measurements: List<Measurement>): RecapUI? {
        if (measurements.isEmpty()) {
            return null
        }

        val sorted = measurements.sortedBy { it.date }
        val first = sorted.first()
        val last = sorted.last()

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
}