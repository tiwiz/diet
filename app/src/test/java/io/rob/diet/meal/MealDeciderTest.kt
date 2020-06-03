package io.rob.diet.meal

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.threeten.bp.LocalTime

class MealDeciderTest {

    private val decider = MealDecider()

    @Test
    fun `if time is between 4 AM and 10 AM elected meal should be breakfast`() {
        checkMealForBounds(4, 10, Meal.BREAKFAST)
    }

    private fun checkMealForBounds(
        lowerBoundOffset: Long,
        upperBoundOffset: Long,
        expectedMeal: Meal) {

        val timeLowerBound = LocalTime.MIDNIGHT.plusHours(lowerBoundOffset)
        val timeUpperBound = LocalTime.MIDNIGHT.plusHours(upperBoundOffset)
            .minusSeconds(1)

        val mealAtLowerBoundOffset = decider.mealForTime(timeLowerBound)
        val mealAtUpperBoundOffset = decider.mealForTime(timeUpperBound)

        assertThat(mealAtLowerBoundOffset).isEqualTo(expectedMeal)
        assertThat(mealAtUpperBoundOffset).isEqualTo(expectedMeal)
    }

    @Test
    fun `if time is between 10 AM and 3 PM elected meal should be lunch`() {
        checkMealForBounds(10, 15, Meal.LUNCH)
    }

    @Test
    fun `if time is between 3 PM and 6 PM elected meal should be afternoon snack`() {
        checkMealForBounds(15, 18, Meal.AFTERNOON_SNACK)
    }

    @Test
    fun `if time is between 6 AM and 10 PM elected meal should be dinner`() {
        checkMealForBounds(18, 22, Meal.DINNER)
    }

    @Test
    fun `if time is between 10 PM and 4 AM elected meal should be night snack`() {
        checkMealForBounds(22, 4, Meal.NIGHT_SNACK)
    }
}