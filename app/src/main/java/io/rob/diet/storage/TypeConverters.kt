package io.rob.diet.storage

import androidx.room.TypeConverter
import io.rob.diet.meal.FoodType
import io.rob.diet.meal.Meal
import io.rob.diet.meal.Protein

class FoodTypeConverter {

    @TypeConverter
    fun toDatabase(foodType: FoodType) = foodType.value

    @TypeConverter
    fun fromDatabase(value: String) = FoodType.from(value)
}

class MealTypeConverter {

    @TypeConverter
    fun toDatabase(meal: Meal) = meal.id

    @TypeConverter
    fun fromDatabase(value: String) = Meal.from(value)
}

class ProteinTypeConverter {

    @TypeConverter
    fun toDatabase(protein: Protein) = protein.value

    @TypeConverter
    fun fromDatabase(value: String) = Protein.from(value)
}




