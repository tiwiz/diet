package io.rob.diet.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.rob.diet.meal.MealPortion
import io.rob.diet.meal.ProteinPerDay
import io.rob.diet.meal.SnackPortion

@Database(entities = [MealPortion::class, SnackPortion::class, ProteinPerDay::class], version = 1)
@TypeConverters(ProteinTypeConverter::class, MealTypeConverter::class, FoodTypeConverter::class)
abstract class DietDatabase : RoomDatabase() {

    abstract fun dao() : DietDao

    companion object {

        fun buildDataBase(context: Context, dbName : String = "diet_database.db") =
            Room.databaseBuilder(context, DietDatabase::class.java, dbName)
                .createFromAsset("database/starting_database.db")
                .build()
    }
}