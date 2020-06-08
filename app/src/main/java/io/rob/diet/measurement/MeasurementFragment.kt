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

        binding.weightWrapper.setOnFocusBehaviour()
        binding.bmiWrapper.setOnFocusBehaviour()
        binding.bodyFatWrapper.setOnFocusBehaviour()
        binding.waistWrapper.setOnFocusBehaviour()
        binding.umbilicalWrapper.setOnFocusBehaviour()
        binding.hipWrapper.setOnFocusBehaviour()

        viewModel.status.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigateUp()
            }
        })

        return binding.root
    }

    private fun TextInputLayout.setOnFocusBehaviour() {
        setOnFocusChangeListener { _, _ ->
            error = null
        }
    }

    private fun TextInputLayout.validOrSetError(): Boolean =
        if (editText?.text.toString().isNotBlank()) {
            true
        } else {
            error = errorMessage
            false
        }

    private fun TextInputLayout.floatValue(): Float =
        editText!!.text.toString().toFloat()

    private fun FragmentMeasurementBinding.checkFormValidity(): Boolean =
        listOf(weightWrapper, bmiWrapper, bodyFatWrapper,
            waistWrapper, umbilicalWrapper, hipWrapper
        ).all { it.validOrSetError() }

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
        }
    }
}