package com.rob.foodlist.meal

import com.rob.foodlist.meal.Meal.*
import org.threeten.bp.LocalTime
import org.threeten.bp.LocalTime.*
import javax.inject.Inject

class MealDecider @Inject constructor(){

    fun mealForTime(time: LocalTime = now()) : Meal =
        when {
            time.isBetween(HOURS, 10) -> BREAKFAST
            time.isBetween(10, 15) -> LUNCH
            time.isBetween(15, 18) -> AFTERNOON_SNACK
            time.isBetween(18, 22) -> DINNER
            else -> NIGHT_SNACK
        }

    private fun LocalTime.isBetween(lowerBoundOffsetInHours: Long,
                                    upperBoundOffsetInHours: Long) : Boolean {
        val lowerBound = MIDNIGHT.plusHours(lowerBoundOffsetInHours).minusSeconds(1)
        val upperBound = MIDNIGHT.plusHours(upperBoundOffsetInHours)
        return isAfter(lowerBound) && isBefore(upperBound)
    }

    companion object {

        private const val HOURS = 4L

        val NIGHT_SNACK_LOWER_BOUND = MIDNIGHT.plusHours(HOURS)
    }
}
