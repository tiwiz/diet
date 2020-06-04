package io.rob.diet.storage

import androidx.room.Dao
import androidx.room.Query
import io.rob.diet.meal.*


@Dao
interface DietDao {

    @Query("SELECT * FROM protein WHERE meal = :meal AND day = :day")
    suspend fun getProteinByMealAndDay(meal: Meal, day: Int): ProteinPerDay

    @Query("SELECT * FROM snack_portions WHERE meal = :meal")
    suspend fun getSnackPortionsByMeal(meal: Meal): List<SnackPortion>

    @Query("SELECT * FROM meal_portions WHERE type = :type")
    suspend fun getFoodAlternativesByType(type: FoodType): List<MealPortion>
}