package io.rob.diet.meal

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test
import org.threeten.bp.LocalDate


class DayDeciderTest {

    private val dayDecider = DayDecider()

    @Test
    fun `verify Monday is day 1`() {
        val monday = LocalDate.of(2020, 6, 8)

        assertThat(dayDecider.getCurrentDayOfTheWeek(monday)).isEqualTo(1)
    }

    @Test
    fun `verify Sunday is day 7`() {
        val monday = LocalDate.of(2020, 6, 7)

        assertThat(dayDecider.getCurrentDayOfTheWeek(monday)).isEqualTo(7)
    }
}