package io.rob.diet.meal

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.rob.diet.R

data class FoodElement(
    val definition: String? = null,
    @StringRes val definitionRes: Int? = null,
    @DrawableRes val iconRes: Int? = null,
    val typeDetail: String? = null)

data class MealUi(
    @StringRes val titleRes: Int,
    @ColorRes val backgroundColorRes: Int,
    @DrawableRes val backgroundImageRes: Int,
    val elements: List<FoodElement>
)

enum class MealUiStaticData(
    @ColorRes val backgroundColorRes: Int,
    @DrawableRes val iconRes: Int
) {
    BREAKFAST(R.color.breakfast_background_color, R.drawable.ic_breakfast_background),
    LUNCH(R.color.lunch_background_color, R.drawable.ic_lunch_background),
    AFTERNOON_SNACK(R.color.afternoon_snack_background_color, R.drawable.ic_afternoon_snack_background),
    DINNER(R.color.dinner_background_color, R.drawable.ic_dinner_background),
    NIGHT_SNACK(R.color.night_snack_background_color, R.drawable.ic_night_snack_background);

    companion object {
        fun from(meal: Meal) : MealUiStaticData =
            when(meal) {
                Meal.BREAKFAST -> BREAKFAST
                Meal.LUNCH -> LUNCH
                Meal.AFTERNOON_SNACK -> AFTERNOON_SNACK
                Meal.DINNER -> DINNER
                else -> NIGHT_SNACK
            }
    }
}