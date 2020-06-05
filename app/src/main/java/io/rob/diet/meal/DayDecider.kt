package io.rob.diet.meal

import org.threeten.bp.LocalDate
import javax.inject.Inject

class DayDecider @Inject constructor(){

    fun getCurrentDayOfTheWeek(day: LocalDate = LocalDate.now()) : Int = day.dayOfWeek.value
}