package io.rob.diet.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.rob.diet.R
import io.rob.diet.databinding.FragmentNewMealBinding


class NewMealFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewMealBinding

    private val types by lazy {
        FoodType.values().map { requireContext().getString(it.stringResId) }.toTypedArray()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindFoodTypeComboBox()
    }

    private fun bindFoodTypeComboBox() {
        val adapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_food_item, types)
        binding.foodType.setAdapter(adapter)
    }
}