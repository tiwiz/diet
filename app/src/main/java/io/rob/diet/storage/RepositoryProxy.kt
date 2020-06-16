package io.rob.diet.storage

import io.rob.diet.common.AuthenticationManager
import io.rob.diet.progress.Measurement
import javax.inject.Inject

class RepositoryProxy @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ProgressRepository{

    override suspend fun getMeasurements(): List<Measurement> =
        if (authenticationManager.isUserAuthenticated()) {
            remoteRepository.getMeasurements()
        } else {
            localRepository.getMeasurements()
        }

    override suspend fun insertNewMeasurement(measurement: Measurement) {
        if (authenticationManager.isUserAuthenticated()) {
            remoteRepository.insertNewMeasurement(measurement)
        } else {
            localRepository.insertNewMeasurement(measurement)
        }
    }
}