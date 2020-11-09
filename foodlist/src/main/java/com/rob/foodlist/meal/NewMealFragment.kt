package com.rob.foodlist.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.R
import io.rob.diet.databinding.FragmentNewMealBinding


@AndroidEntryPoint
class NewMealFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewMealBinding
    private val viewModel by viewModels<NewMealViewModel>()

    private val types by lazy {
        viewModel.foodTypes.map { requireContext().getString(it.stringResId) }.toTypedArray()
    }

    private val errorMessage by lazy {
        requireContext().getString(R.string.empty_mandatory_field_error)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewMealBinding.inflate(inflater, container, false)

        binding.setOnFocusBehaviour()

        binding.foodType.setOnItemClickListener { _, _, position, _ ->
            viewModel.onFoodTypeSelected(position)
        }

        binding.foodType.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveData()
                true
            } else {
                false
            }
        }

        binding.btnSave.setOnClickListener { saveData() }

        return binding.root
    }

    private fun saveData() {
        if (binding.checkFormValidity()) {
            val portion = RemoteMealPortion(
                definition = binding.foodDescription.text.toString(),
                weight = binding.foodNumber.text!!.toString().toInt(),
                unit = binding.foodUnit.text.toString()
            )

            viewModel.saveRemotePortion(portion)
            findNavController().navigateUp()
        }
    }

    private fun FragmentNewMealBinding.checkFormValidity(): Boolean =
        foodDescriptionLayout.validOrSetError(errorMessage) && checkComboBoxValidity()

    private fun FragmentNewMealBinding.checkComboBoxValidity(): Boolean =
        if (viewModel.isValidFoodType()) {
            true
        } else {
            foodTypeLayout.error = errorMessage
            false
        }

    private fun FragmentNewMealBinding.setOnFocusBehaviour() =
        listOf(foodDescriptionLayout, foodUnitLayout ,foodTypeLayout)
            .forEach { it.setOnFocusBehaviour() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindFoodTypeComboBox()
    }

    private fun bindFoodTypeComboBox() {
        val adapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_food_item, types)
        binding.foodType.setAdapter(adapter)
    }
}
