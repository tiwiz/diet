package io.rob.diet.progress

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.Test


class MeasurementTransformerTest {

    private val transformer = MeasurementTransformer()

    @Test
    fun `verify recap is null if no data is available`() {
        assertThat(transformer.toRecapUI(emptyList())).isNull()
    }

    @Test
    fun `verify only first and last measurements are taken into account into the recap`() {
        val actualRecapUi = transformer.toRecapUI(measurements)

        assertThat(actualRecapUi).isEqualTo(expectedRecapUI)
    }
}