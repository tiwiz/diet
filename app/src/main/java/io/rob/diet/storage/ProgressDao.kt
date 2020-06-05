package io.rob.diet.storage

import androidx.room.*
import io.rob.diet.progress.Measurement

@Dao
interface ProgressDao {

    @Query("SELECT * FROM progress")
    suspend fun getMeasurements() : List<Measurement>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewMeasurement(measurement: Measurement)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeasurement(measurement: Measurement)

    @Delete
    suspend fun deleteMeasurement(measurement: Measurement)
}