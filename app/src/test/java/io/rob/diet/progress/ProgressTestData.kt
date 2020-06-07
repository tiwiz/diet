package io.rob.diet.progress

import org.threeten.bp.LocalDate

val first = Measurement(
    id = 0,
    weight = 100f,
    bmi = 33f,
    waist = 100f,
    umbilical = 100f,
    hip = 100f,
    bodyFatPct = 33f,
    date = LocalDate.of(2020, 1, 31)
)

val second = Measurement(
    id = 1,
    weight = 90f,
    bmi = 32f,
    waist = 90f,
    umbilical = 90f,
    hip = 90f,
    bodyFatPct = 32f,
    date = LocalDate.of(2020, 2, 28)
)

val third = Measurement(
    id = 2,
    weight = 80f,
    bmi = 31f,
    waist = 80f,
    umbilical = 80f,
    hip = 80f,
    bodyFatPct = 31f,
    date = LocalDate.of(2020, 3, 28)
)

val fourth = Measurement(
    id = 3,
    weight = 70f,
    bmi = 30f,
    waist = 70f,
    umbilical = 70f,
    hip = 70f,
    bodyFatPct = 30f,
    date = LocalDate.of(2020, 4, 28)
)

val measurements = listOf(second, first, fourth, third)

val expectedRecapUI = RecapUI(
    weightStart = 100f,
    bmiStart = 33f,
    waistStart = 100f,
    umbilicalStart = 100f,
    hipStart = 100f,
    bodyFatPctStart = 33f,
    weightEnd = 70f,
    bmiEnd = 30f,
    waistEnd = 70f,
    umbilicalEnd = 70f,
    hipEnd = 70f,
    bodyFatPctEnd = 30f,
    weightDelta = -30f,
    bmiDelta = -3f,
    waistDelta = -30f,
    umbilicalDelta = -30f,
    hipDelta = -30f,
    bodyFatPctDelta = -3f
)