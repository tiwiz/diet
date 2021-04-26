package io.rob.diet.charts

data class ChartModel(
    val title: String,
    val points: Array<Float>,
    val descriptions: Array<String>
)

data class ChartRowElement(
    val index: Int,
    val value: Float
)