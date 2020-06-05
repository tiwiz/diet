package io.rob.diet.meal

import io.rob.diet.meal.MealDecider.Companion.NIGHT_SNACK_LOWER_BOUND
import org.threeten.bp.LocalTime
import org.threeten.bp.LocalTime.MIDNIGHT
import org.threeten.bp.LocalTime.now
import javax.inject.Inject

class AlternativeMealDecider @Inject constructor() {

    private val meals =
        listOf(Meal.BREAKFAST, Meal.LUNCH, Meal.AFTERNOON_SNACK, Meal.DINNER, Meal.NIGHT_SNACK)

    fun nextMealFrom(meal: Meal, day: Int, time: LocalTime = now()): Pair<Meal, Int> {
        val currentIndex = meals.indexOfFirst { it == meal }
        var nextIndex: Int = 0
        var newDay = day

        if (currentIndex == meals.size - 1) {
            nextIndex = 0
            newDay = nextDayOf(day, time)
        } else {
            nextIndex = currentIndex + 1
        }

        return meals[nextIndex] to newDay
    }

    private fun nextDayOf(day: Int, time: LocalTime): Int {
        if (time.isBefore(NIGHT_SNACK_LOWER_BOUND)) {
            return day
        }

        return if (day == 7) {
            1
        } else {
            day + 1
        }
    }

    fun previousMealFrom(meal: Meal, day: Int, time: LocalTime = now()): Pair<Meal, Int> {
        val currentIndex = meals.indexOfFirst { it == meal }
        val nextIndex: Int
        var newDay = day

        if (currentIndex == 0) {
            nextIndex = meals.size - 1
            newDay = previousDayOf(day)
        } else {
            nextIndex = currentIndex - 1
            if (time.isBefore(NIGHT_SNACK_LOWER_BOUND)) {
                newDay = previousDayOf(day)
            }
        }

        return meals[nextIndex] to newDay
    }

    private fun previousDayOf(day: Int): Int {
        return if (day == 1 ) {
            7
        } else {
            day - 1
        }
    }
}