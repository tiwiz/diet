package com.rob.foodlist.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.R
import io.rob.diet.databinding.FragmentAlternativeMealBinding

@AndroidEntryPoint
class AlternativeMealFragment : BottomSheetDialogFragment() {

    private val sharedVM by activityViewModels<AlternativeMealViewModel>()
    private val args by navArgs<AlternativeMealFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAlternativeMealBinding.inflate(inflater, container, false)

        updateSelectedMeal(args.meal, binding)

        binding.meals.setOnCheckedChangeListener { _, checkedId ->
            onMealSelected(checkedId, binding.days.checkedRadioButtonId)
        }

        updateSelectedDay(args.day, binding)

        binding.days.setOnCheckedChangeListener { _, checkedId ->
            onMealSelected(binding.meals.checkedRadioButtonId, checkedId)
        }

        return binding.root
    }

    private fun updateSelectedDay(day: Int, binding: FragmentAlternativeMealBinding) {
        when(day) {
            1 -> binding.day1.isChecked = true
            2 -> binding.day2.isChecked = true
            3 -> binding.day3.isChecked = true
            4 -> binding.day4.isChecked = true
            5 -> binding.day5.isChecked = true
            6 -> binding.day6.isChecked = true
            else -> binding.day7.isChecked = true
        }
    }

    private fun updateSelectedMeal(mealId: String, binding: FragmentAlternativeMealBinding) {
        when(Meal.from(mealId)) {
            Meal.BREAKFAST -> binding.breakfast.isChecked = true
            Meal.LUNCH -> binding.lunch.isChecked = true
            Meal.AFTERNOON_SNACK -> binding.afternoonSnack.isChecked = true
            Meal.DINNER -> binding.dinner.isChecked = true
            else -> binding.nightSnack.isChecked = true
        }
    }

    private fun onMealSelected(mealId: Int, dayId: Int) {
        val meal = when (mealId) {
            R.id.breakfast -> Meal.BREAKFAST
            R.id.lunch -> Meal.LUNCH
            R.id.afternoon_snack -> Meal.AFTERNOON_SNACK
            R.id.dinner -> Meal.DINNER
            else -> Meal.NIGHT_SNACK
        }

        val day = when(dayId) {
            R.id.day1 -> 1
            R.id.day2 -> 2
            R.id.day3 -> 3
            R.id.day4 -> 4
            R.id.day5 -> 5
            R.id.day6 -> 6
            else -> 7
        }

        sharedVM.updateMeal(meal, day)
    }
}
