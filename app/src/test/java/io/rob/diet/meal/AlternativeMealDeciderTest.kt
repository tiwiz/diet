package io.rob.diet.meal

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.threeten.bp.LocalTime

class AlternativeMealDeciderTest {

    private val decider = AlternativeMealDecider()

    private val beforeMidnight = LocalTime.NOON
    private val afterMidnight = LocalTime.MIDNIGHT.plusMinutes(1)

    @Test
    fun `verify next meal of breakfast is lunch of the same day`() {
        val (meal, day) = decider.nextMealFrom(Meal.BREAKFAST, 1, beforeMidnight)

        assertThat(meal).isEqualTo(Meal.LUNCH)
        assertThat(day).isEqualTo(1)
    }

    @Test
    fun `verify next meal of night snack is breakfast of the next day`() {
        val (meal, day) = decider.nextMealFrom(Meal.NIGHT_SNACK, 1, beforeMidnight)

        assertThat(meal).isEqualTo(Meal.BREAKFAST)
        assertThat(day).isEqualTo(2)
    }

    @Test
    fun `verify next meal of night snack on last day is breakfast of the first day`() {
        val (meal, day) = decider.nextMealFrom(Meal.NIGHT_SNACK, 7, beforeMidnight)

        assertThat(meal).isEqualTo(Meal.BREAKFAST)
        assertThat(day).isEqualTo(1)
    }

    @Test
    fun `verify previous meal of breakfast is night snack of the day before`() {
        val (meal, day) = decider.previousMealFrom(Meal.BREAKFAST, 2, beforeMidnight)

        assertThat(meal).isEqualTo(Meal.NIGHT_SNACK)
        assertThat(day).isEqualTo(1)
    }

    @Test
    fun `verify previous meal of night snack is dinner of the same day`() {
        val (meal, day) = decider.previousMealFrom(Meal.NIGHT_SNACK, 1, beforeMidnight)

        assertThat(meal).isEqualTo(Meal.DINNER)
        assertThat(day).isEqualTo(1)
    }

    @Test
    fun `verify previous meal of breakfast on the first day is night snack of the last day`() {
        val (meal, day) = decider.previousMealFrom(Meal.BREAKFAST, 1, beforeMidnight)

        assertThat(meal).isEqualTo(Meal.NIGHT_SNACK)
        assertThat(day).isEqualTo(7)
    }

    @Test
    fun `verify previous meal of night snack after midnight is dinner of previous day`() {
        val (meal, day) = decider.previousMealFrom(Meal.NIGHT_SNACK, 2, afterMidnight)

        assertThat(meal).isEqualTo(Meal.DINNER)
        assertThat(day).isEqualTo(1)
    }

    @Test
    fun `verify next meal of night snack after midnight is breakfast of the same day`() {
        val (meal, day) = decider.nextMealFrom(Meal.NIGHT_SNACK, 2, afterMidnight)

        assertThat(meal).isEqualTo(Meal.BREAKFAST)
        assertThat(day).isEqualTo(2)
    }
}