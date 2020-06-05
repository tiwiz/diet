package io.rob.diet.progress

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "progress")
data class Measurement(
    @PrimaryKey val id: Int,
    val weight: Float, //peso
    val bmi: Float, //bmi
    val waist: Float, //Circonferenza vita
    val umbilical: Float, //Circonferenza ombelicale
    val hip: Float, //Circonferenza fianchi
    val bodyFatPct: Float, //Massa grassa
    val date: LocalDate
)