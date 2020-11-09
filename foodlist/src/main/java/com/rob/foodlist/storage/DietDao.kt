package com.rob.foodlist.storage

import androidx.room.Dao
import androidx.room.Query
import com.rob.foodlist.meal.*


@Dao
interface DietDao {

    @Query("SELECT * FROM portions WHERE meal = :meal AND day = :day")
    suspend fun getPortionByMealAndDay(meal: com.rob.foodlist.meal.Meal, day: Int): com.rob.foodlist.meal.PortionPerDay

    @Query("SELECT * FROM snack_portions WHERE meal = :meal")
    suspend fun getSnackPortionsByMeal(meal: com.rob.foodlist.meal.Meal): List<com.rob.foodlist.meal.SnackPortion>

    @Query("SELECT * FROM meal_portions WHERE type = :type")
    suspend fun getFoodAlternativesByType(type: com.rob.foodlist.meal.FoodType): List<com.rob.foodlist.meal.MealPortion>
}
