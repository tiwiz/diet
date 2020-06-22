package io.rob.diet.storage

import io.rob.diet.common.AuthenticationManager
import io.rob.diet.progress.Measurement
import timber.log.Timber
import javax.inject.Inject

class RepositoryProxy @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ProgressRepository{

    override suspend fun getMeasurements(): List<Measurement> =
        if (authenticationManager.isUserAuthenticated()) {
            Timber.d("Fetching from remote")
            remoteRepository.getMeasurements()
        } else {
            Timber.d("Fetching from local")
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