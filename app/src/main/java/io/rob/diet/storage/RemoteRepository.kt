package io.rob.diet.storage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import io.rob.diet.common.await
import io.rob.diet.progress.Measurement
import io.rob.diet.progress.RemoteMeasurements
import io.rob.diet.progress.toMeasurement
import io.rob.diet.progress.toRemoteMeasurements
import javax.inject.Inject

class RemoteRepository @Inject constructor() : ProgressRepository{

    private val db by lazy {
        Firebase.database.getReference(DB_REFERENCE)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
    }

    override suspend fun getMeasurements(): List<Measurement> =
        remoteMeasurements().map { it.toMeasurement() }

    private suspend fun remoteMeasurements() : List<RemoteMeasurements> =
        db.await().getValue<List<RemoteMeasurements>>()
            .orEmpty()

    override suspend fun insertNewMeasurement(measurement: Measurement) {
        val elements = remoteMeasurements() + measurement.toRemoteMeasurements()
        db.setValue(elements)
    }

    companion object {
        private const val DB_REFERENCE = "measurements"
    }
}