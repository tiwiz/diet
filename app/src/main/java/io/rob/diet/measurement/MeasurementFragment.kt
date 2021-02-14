package io.rob.diet.measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.databinding.FragmentMeasurementBinding
import io.rob.diet.progress.UiMeasurement
import io.rob.diet.ui.theme.DietTheme

@AndroidEntryPoint
class MeasurementFragment : Fragment() {

    private val viewModel by activityViewModels<MeasureViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentMeasurementBinding.inflate(layoutInflater, container, false).apply {
            root.setContent {
                DietTheme {
                    MeasurementUI { saveData(it) }
                }
            }
        }.root

    private fun saveData(measurement: UiMeasurement) {
        viewModel.saveData(measurement.toMeasurement())
        findNavController().navigateUp()
    }
}