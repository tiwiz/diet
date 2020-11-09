package com.rob.foodlist.meal

import androidx.annotation.DrawableRes
import io.rob.diet.R
import com.rob.foodlist.meal.FoodType.*
import io.rob.diet.storage.DietDao
import javax.inject.Inject

class MealRepository @Inject constructor(private val dietDao: DietDao) {

    suspend fun fetchDataForMealAndDay(meal: Meal, day: Int) : List<FoodElement> {
        return if (meal == Meal.LUNCH || meal == Meal.DINNER) {
            wrapMainCourses(dietDao.getPortionByMealAndDay(meal, day))
        } else {
            val portions = dietDao.getSnackPortionsByMeal(meal)
            wrapSecondaryCourses(portions)
        }
    }

    private fun wrapMainCourses(portionPerDay: PortionPerDay) : List<FoodElement> =
        if (portionPerDay.protein == Protein.PIZZA) {
            listOf(
                portionPerDay.toUiData(),
                FRUIT.toUiData()
            )
        } else {
            listOf(
                CARBS.toUiData(),
                portionPerDay.toUiData(),
                VEGGIES.toUiData(),
                FRUIT.toUiData()
            )
        }

    private fun FoodType.toUiData() = FoodElement(
        definitionRes = stringResId,
        typeDetail = value
    )

    private fun PortionPerDay.toUiData() = FoodElement(
        definitionRes = protein.stringResId,
        typeDetail = protein.value
    )

    private fun wrapSecondaryCourses(snackPortions: List<SnackPortion>) : List<FoodElement> =
        snackPortions.map { portion ->
            FoodElement(definition = portion.weightDefinition(),
                iconRes = fetchIconByType(portion.meal, portion.group),
                typeDetail = portion.type?.value
            )
        }

    private fun SnackPortion.weightDefinition() : String {
        val weight = listOfNotNull(weight, unit).joinToString(separator = " ", prefix = "(", postfix = ")")

        return "$definition $weight"
    }

    @DrawableRes
    private fun fetchIconByType(meal: Meal, group: Int) : Int? {
        if (meal != Meal.BREAKFAST) {
          return null
        }

        return if (group == 1) {
            R.drawable.ic_drink
        } else {
            R.drawable.ic_food
        }
    }
}
