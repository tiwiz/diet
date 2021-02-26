package io.rob.diet.charts

import android.app.Application
import androidx.annotation.StringRes
import io.rob.diet.Charts
import io.rob.diet.R
import io.rob.diet.progress.Measurement
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class ChartDataFilter @Inject constructor(private val app: Application) {

    private val pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun toChartData(type: Charts, input: List<Measurement>) =
        when (type) {
            Charts.WAIST -> fetchWaist(input)
            Charts.UMBILICAL -> fetchUmbilical(input)
            Charts.HIP -> fetchHip(input)
            Charts.WEIGHT -> fetchWeight(input)
            Charts.BODY_FAT -> fetchBodyFat(input)
            Charts.BMI -> fetchBmi(input)
        }

    private fun fetchBmi(input: List<Measurement>): ChartModel =
        fetch(input, R.string.bmi) { bmi }

    private fun fetch(
        input: List<Measurement>,
        @StringRes titleRes: Int,
        transformation: Measurement.() -> Float
    ): ChartModel {

        val points = mutableListOf<Float>()
        val descriptions = mutableListOf<String>()

        input.forEachIndexed { index, measurement ->
            points.add(index, transformation(measurement))
            descriptions.add(index, measurement.date.format(pattern))
        }

        return ChartModel(
            title = app.getString(titleRes),
            points = points.toTypedArray(),
            descriptions = descriptions.toTypedArray()
        )
    }

    private fun fetchBodyFat(input: List<Measurement>): ChartModel =
        fetch(input, R.string.body_fat) { bodyFatPct }

    private fun fetchWeight(input: List<Measurement>): ChartModel =
        fetch(input, R.string.weight_hint) { weight }

    private fun fetchWaist(input: List<Measurement>): ChartModel =
        fetch(input, R.string.waist) { waist }

    private fun fetchUmbilical(input: List<Measurement>): ChartModel =
        fetch(input, R.string.umbilical) { umbilical }

    private fun fetchHip(input: List<Measurement>): ChartModel =
        fetch(input, R.string.hip) { hip }

}