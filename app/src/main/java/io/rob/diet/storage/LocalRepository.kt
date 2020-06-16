package io.rob.diet.storage

import io.rob.diet.progress.Measurement
import javax.inject.Inject

class LocalRepository @Inject constructor(private val dao: ProgressDao) : ProgressRepository {

    override suspend fun getMeasurements(): List<Measurement> =
        dao.getMeasurements()

    override suspend fun insertNewMeasurement(measurement: Measurement) =
        dao.insertNewMeasurement(measurement)
}