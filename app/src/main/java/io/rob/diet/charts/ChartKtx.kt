package io.rob.diet.charts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

fun Array<Float>.wrap(): List<Pair<ChartRowElement, ChartRowElement?>> =
    mapIndexed { index, value -> ChartRowElement(index, value) }
        .chunked(2) { it[0] to if (it.size > 1) it[1] else null }

@Composable
fun Array<Float>.ChartRow(
    action: @Composable RowScope.(left: ChartRowElement, right: ChartRowElement?) -> Unit
) {
    wrap().forEach {
        Row {
            action(it.first, it.second)
        }
    }
}