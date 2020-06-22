package io.rob.diet.measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.R
import io.rob.diet.common.setOnFocusBehaviour
import io.rob.diet.common.validOrSetError
import io.rob.diet.databinding.FragmentMeasurementBinding
import io.rob.diet.progress.Measurement
import org.threeten.bp.LocalDate

@AndroidEntryPoint
class MeasurementFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMeasurementBinding

    private val viewModel by activityViewModels<MeasureViewModel>()

    private val errorMessage by lazy {
        requireContext().getString(R.string.empty_mandatory_field_error)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMeasurementBinding.inflate(inflater, container, false)
        binding.saveButton.setOnClickListener { saveData() }

        binding.setOnFocusBehaviour()

        return binding.root
    }

    private fun TextInputLayout.floatValue(): Float =
        editText!!.text.toString().toFloat()

    private val FragmentMeasurementBinding.layouts
        get() = listOf(
            weightWrapper, bmiWrapper, bodyFatWrapper,
            waistWrapper, umbilicalWrapper, hipWrapper
        )

    private fun FragmentMeasurementBinding.checkFormValidity(): Boolean =
        layouts.all { it.validOrSetError(errorMessage) }

    private fun FragmentMeasurementBinding.setOnFocusBehaviour() =
        layouts.forEach { it.setOnFocusBehaviour() }

    private fun saveData() {
        if (binding.checkFormValidity()) {
            val measurement = Measurement(
                weight = binding.weightWrapper.floatValue(),
                bmi = binding.bmiWrapper.floatValue(),
                waist = binding.waistWrapper.floatValue(),
                umbilical = binding.umbilicalWrapper.floatValue(),
                hip = binding.hipWrapper.floatValue(),
                bodyFatPct = binding.bodyFatWrapper.floatValue(),
                date = LocalDate.now()
            )

            viewModel.saveData(measurement)
            findNavController().navigateUp()
        }
    }
}