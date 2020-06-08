package io.rob.diet.progress

import androidx.room.Entity
import androidx.room.PrimaryKey
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