package io.rob.diet.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.rob.diet.meal.MealPortion
import io.rob.diet.meal.PortionPerDay
import io.rob.diet.meal.SnackPortion
import io.rob.diet.progress.Measurement

@Database(entities = [MealPortion::class, SnackPortion::class, PortionPerDay::class, Measurement::class], version = 1)
@TypeConverters(ProteinTypeConverter::class, MealTypeConverter::class, FoodTypeConverter::class, LocalDateTypeConverter::class)
abstract class DietDatabase : RoomDatabase() {

    abstract fun dietDao() : DietDao

    abstract fun progressDao() : ProgressDao

    companion object {

        fun buildDataBase(context: Context, dbName : String = "diet_database.db") =
            Room.databaseBuilder(context, DietDatabase::class.java, dbName)
                .createFromAsset("database/starting_database.db")
                .build()
    }
}