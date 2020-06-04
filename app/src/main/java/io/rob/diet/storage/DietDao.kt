package io.rob.diet.storage

import androidx.room.Dao
import androidx.room.Query
import io.rob.diet.meal.Meal
import io.rob.diet.meal.ProteinPerDay


@Dao
interface DietDao {

    @Query("SELECT * FROM protein WHERE meal = :meal AND day = :day")
    suspend fun getProteinByMealAndDay(meal: Meal, day: Int) : ProteinPerDay
}