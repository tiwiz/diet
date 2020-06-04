package io.rob.diet.meal

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.rob.diet.R
import io.rob.diet.storage.FoodTypeConverter
import io.rob.diet.storage.MealTypeConverter
import io.rob.diet.storage.ProteinTypeConverter


enum class Meal(val id: String) {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    AFTERNOON_SNACK("afternoon_snack"),
    DINNER("dinner"),
    NIGHT_SNACK("night_snack");

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

@Entity(tableName = "snack_portions")
data class SnackPortion(
    @PrimaryKey val id: Int,
    val definition: String,
    val weight: Int,
    val unit: String?,
    val meal: Meal,
    val group: Int
)

@Entity(tableName = "protein")
data class ProteinPerDay(
    @PrimaryKey val id: Int,
    val day: Int,
    val protein: Protein,
    val meal: Meal
)