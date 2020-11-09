package com.rob.foodlist.storage

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate

class FoodTypeConverter {

    @TypeConverter
    fun toDatabase(foodType: com.rob.foodlist.meal.FoodType) = foodType.value

    @TypeConverter
    fun fromDatabase(value: String?) = value?.let { com.rob.foodlist.meal.FoodType.from(value) }
}

class MealTypeConverter {

    @TypeConverter
    fun toDatabase(meal: com.rob.foodlist.meal.Meal) = meal.id

    @TypeConverter
    fun fromDatabase(value: String) = com.rob.foodlist.meal.Meal.from(value)
}

class ProteinTypeConverter {

    @TypeConverter
    fun toDatabase(protein: com.rob.foodlist.meal.Protein) = protein.value

    @TypeConverter
    fun fromDatabase(value: String) = com.rob.foodlist.meal.Protein.from(value)
}

class LocalDateTypeConverter {

    @TypeConverter
    fun toDatabase(localDate: LocalDate) = localDate.toString()

    @TypeConverter
    fun fromDatabase(value: String) = LocalDate.parse(value)
}




