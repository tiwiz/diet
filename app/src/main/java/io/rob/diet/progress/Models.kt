package io.rob.diet.progress

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import org.threeten.bp.LocalDate

@Entity(tableName = "progress")
data class Measurement(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val weight: Float,
    val bmi: Float,
    val waist: Float,
    val umbilical: Float,
    val hip: Float,
    val bodyFatPct: Float,
    val date: LocalDate
)

data class RemoteMeasurements(
    val weight: Float? = -1f,
    val bmi: Float? = -1f,
    val waist: Float? = -1f,
    val umbilical: Float? = -1f,
    val hip: Float? = -1f,
    val bodyFatPct: Float? = -1f,
    val date: Long? = -1L
)

data class UiMeasurement(
    var weight: Float = -1f,
    var bmi: Float = -1f,
    var waist: Float = -1f,
    var umbilical: Float = -1f,
    var hip: Float = -1f,
    var bodyFatPct: Float = -1f,
) {

    fun toMeasurement() = Measurement(
        weight = weight,
        bmi = bmi,
        waist = waist,
        umbilical = umbilical,
        hip = hip,
        bodyFatPct = bodyFatPct,
        date = LocalDate.now()
    )
}

fun Measurement.toRemoteMeasurements() =
    RemoteMeasurements(
        weight = weight,
        bmi = bmi,
        waist = waist,
        umbilical = umbilical,
        hip = hip,
        bodyFatPct = bodyFatPct,
        date = date.toEpochDay()
    )

fun RemoteMeasurements.toMeasurement() =
    Measurement(
        weight = weight!!,
        bmi = bmi!!,
        waist = waist!!,
        umbilical = umbilical!!,
        hip = hip!!,
        bodyFatPct = bodyFatPct!!,
        date = LocalDate.ofEpochDay(date!!)
    )