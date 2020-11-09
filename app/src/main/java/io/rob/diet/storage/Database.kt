package io.rob.diet.storage

import android.content.Context
import androidx.room.*
import io.rob.diet.progress.Measurement
import org.threeten.bp.LocalDate

@Database(entities = [Measurement::class], version = 1)
@TypeConverters(LocalDateTypeConverter::class)
abstract class DietDatabase : RoomDatabase() {


    abstract fun progressDao() : ProgressDao

    companion object {

        fun buildDataBase(context: Context, dbName : String = "diet_database.db") =
            Room.databaseBuilder(context, DietDatabase::class.java, dbName)
                .createFromAsset("database/starting_database.db")
                .build()
    }
}

class LocalDateTypeConverter {

    @TypeConverter
    fun toDatabase(localDate: LocalDate) = localDate.toString()

    @TypeConverter
    fun fromDatabase(value: String) = LocalDate.parse(value)
}
