package io.rob.diet.storage

import androidx.annotation.DrawableRes
import io.rob.diet.R
import io.rob.diet.meal.*
import io.rob.diet.meal.FoodType.*
import javax.inject.Inject

class MealRepository @Inject constructor(private val dietDao: DietDao) {

    suspend fun fetchDataForMealAndDay(meal: Meal, day: Int) : List<UiData> {
        return if (meal == Meal.LUNCH || meal == Meal.DINNER) {
            wrapMainCourses(dietDao.getProteinByMealAndDay(meal, day))
        } else {
            val portions = dietDao.getSnackPortionsByMeal(meal)
            wrapSecondaryCourses(portions)
        }
    }

    private fun wrapMainCourses(proteinPerDay: ProteinPerDay) : List<UiData> =
        if (proteinPerDay.protein == Protein.PIZZA) {
            listOf(
                proteinPerDay.toUiData(),
                FRUIT.toUiData()
            )
        } else {
            listOf(
                CARBS.toUiData(),
                proteinPerDay.toUiData(),
                VEGGIES.toUiData(),
                FRUIT.toUiData()
            )
        }

    private fun FoodType.toUiData() = UiData(
        definitionRes = stringResId,
        typeDetail = value
    )

    private fun ProteinPerDay.toUiData() = UiData(
        definitionRes = protein.stringResId,
        typeDetail = protein.value
    )

    private fun wrapSecondaryCourses(snackPortions: List<SnackPortion>) : List<UiData> =
        snackPortions.map { portion ->
            UiData(definition = portion.definition,
                weight = portion.weight,
                unit = portion.unit,
                iconRes = fetchIconByType(portion.meal, portion.group)
            )
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