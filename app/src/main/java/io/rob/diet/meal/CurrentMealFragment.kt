package io.rob.diet.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.common.Lce
import io.rob.diet.common.updateExternalColors
import io.rob.diet.databinding.FragmentCurrentMealBinding
import io.rob.diet.meal.CurrentMealFragmentDirections.Companion.actionCurrentMealFragmentToAlternativeMealFragment


@AndroidEntryPoint
class CurrentMealFragment : Fragment() {

    private lateinit var binding: FragmentCurrentMealBinding

    private val viewModel by viewModels<MealViewModel>()

    private val sharedVM by activityViewModels<AlternativeMealViewModel>()

    private val mealAdapter by lazy {
        MealAdapter(LayoutInflater.from(requireActivity()), this::onTypeSelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentMealBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mealList.layoutManager = LinearLayoutManager(requireContext())
        binding.mealList.adapter = mealAdapter

        viewModel.mealUi.observe(viewLifecycleOwner, Observer {
            if (it is Lce.Success) {
                bindUi(it.data)
            }
        })

        sharedVM.alternativeMeal.observe(viewLifecycleOwner, Observer { (meal, day) ->
            viewModel.fetchMealWithParameters(meal, day)
        })
    }

    private fun bindUi(data: MealUi) {
        val color = ContextCompat.getColor(requireContext(), data.backgroundColorRes)
        binding.root.setBackgroundColor(color)

        updateExternalColors(color, data.navigationColorRes)

        binding.title.setText(data.titleRes)
        binding.backgroundImage.setImageResource(data.backgroundImageRes)

        binding.mealSelection.setOnClickListener {
            val action =
                actionCurrentMealFragmentToAlternativeMealFragment(data.meal.id, data.day)
            findNavController().navigate(action)
        }

        mealAdapter.updateElements(data.elements)
    }

    //TODO show details
    private fun onTypeSelected(query: String) {
        println(query)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchNextMeal()
    }
}