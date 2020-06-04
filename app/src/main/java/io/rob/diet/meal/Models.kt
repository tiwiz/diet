package io.rob.diet.meal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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

enum class FoodType(val value: String) {
    LEGUMES("legumes"),
    EGGS("eggs"),
    CANNED_TUNA("canned_tuna"),
    COLD_CUTS("cold_cuts"),
    CHEESE("cheese"),
    WHITE_MEAT("white_meat"),
    FISH("fish"),
    RED_MEAT("red_meat"),
    PIZZA("pizza"),
    VEGGIES("veggies"),
    FRUIT("fruit"),
    CARBS("carbs");

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

enum class Protein(val value: String) {
    LEGUMES("legumes"),
    EGGS("eggs"),
    CANNED_TUNA("canned_tuna"),
    COLD_CUTS("cold_cuts"),
    CHEESE("cheese"),
    WHITE_MEAT("white_meat"),
    FISH("fish"),
    RED_MEAT("red_meat"),
    PIZZA("pizza");

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