package com.rob.foodlist.meal

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.rob.diet.R


enum class Meal(val id: String, @StringRes val stringResId: Int) {
    BREAKFAST("breakfast", R.string.breakfast_title),
    LUNCH("lunch", R.string.lunch_title),
    AFTERNOON_SNACK("afternoon_snack", R.string.afternoon_snack_title),
    DINNER("dinner", R.string.dinner_title),
    NIGHT_SNACK("night_snack", R.string.night_snack_title);

    companion object {

        fun from(id: String) =
            when (id) {
                "breakfast" -> BREAKFAST
                "lunch" -> LUNCH
                "afternoon_snack" -> AFTERNOON_SNACK
                "dinner" -> DINNER
                else -> NIGHT_SNACK
            }
    }
}

enum class FoodType(val value: String, @StringRes val stringResId: Int) {
    LEGUMES("legumes", R.string.legumes_title),
    EGGS("eggs", R.string.eggs_title),
    CANNED_TUNA("canned_tuna", R.string.canned_tuna_title),
    COLD_CUTS("cold_cuts", R.string.cold_cuts_title),
    CHEESE("cheese", R.string.cheese_title),
    WHITE_MEAT("white_meat", R.string.white_meat_title),
    FISH("fish", R.string.fish_title),
    RED_MEAT("red_meat", R.string.red_meat_title),
    PIZZA("pizza", R.string.pizza_title),
    VEGGIES("veggies", R.string.veggies_title),
    FRUIT("fruit", R.string.fruit_title),
    COOKIES("cookies", R.string.cookies_title),
    CARBS("carbs", R.string.carbohydrates_title);

    companion object {

        fun from(value: String) =
            when (value) {
                "legumes" -> LEGUMES
                "eggs" -> EGGS
                "canned_tuna" -> CANNED_TUNA
                "cold_cuts" -> COLD_CUTS
                "cheese" -> CHEESE
                "white_meat" -> WHITE_MEAT
                "fish" -> FISH
                "red_meat" -> RED_MEAT
                "veggies" -> VEGGIES
                "fruit" -> FRUIT
                "cookies" -> COOKIES
                "carbs" -> CARBS
                else -> PIZZA
            }
    }
}

enum class Protein(val value: String, @StringRes val stringResId: Int) {
    LEGUMES("legumes", R.string.legumes_title),
    EGGS("eggs", R.string.eggs_title),
    CANNED_TUNA("canned_tuna", R.string.canned_tuna_title),
    COLD_CUTS("cold_cuts", R.string.cold_cuts_title),
    CHEESE("cheese", R.string.cheese_title),
    WHITE_MEAT("white_meat", R.string.white_meat_title),
    FISH("fish", R.string.fish_title),
    RED_MEAT("red_meat", R.string.red_meat_title),
    PIZZA("pizza", R.string.pizza_title);

    companion object {

        fun from(value: String) =
            when (value) {
                "legumes" -> LEGUMES
                "eggs" -> EGGS
                "canned_tuna" -> CANNED_TUNA
                "cold_cuts" -> COLD_CUTS
                "cheese" -> CHEESE
                "white_meat" -> WHITE_MEAT
                "fish" -> FISH
                "red_meat" -> RED_MEAT
                else -> PIZZA
            }
    }
}

@Entity(tableName = "meal_portions")
data class MealPortion(
    @PrimaryKey val id: Int,
    val definition: String,
    val weight: Int,
    val unit: String?,
    val type: FoodType
)

data class RemoteMealPortion(
    val definition: String? = null,
    val weight: Int? = null,
    val unit: String? = null,
    val type: String? = null
)

@Entity(tableName = "snack_portions")
data class SnackPortion(
    @PrimaryKey val id: Int,
    val definition: String,
    val weight: Int,
    val unit: String?,
    val meal: Meal,
    val group: Int,
    val type: FoodType?
)

@Entity(tableName = "portions")
data class PortionPerDay(
    @PrimaryKey val id: Int,
    val day: Int,
    val protein: Protein,
    val meal: Meal
)
