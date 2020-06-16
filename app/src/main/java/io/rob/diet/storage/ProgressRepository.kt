package io.rob.diet.storage

import io.rob.diet.progress.Measurement

interface ProgressRepository {

    suspend fun getMeasurements() : List<Measurement>

    suspend fun insertNewMeasurement(measurement: Measurement)
}