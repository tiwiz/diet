package io.rob.diet.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.common.Lce
import io.rob.diet.common.updateExternalColors
import io.rob.diet.databinding.FragmentCurrentMealBinding


@AndroidEntryPoint
class CurrentMealFragment : Fragment() {

    private lateinit var binding: FragmentCurrentMealBinding

    private val viewModel by viewModels<MealViewModel>()

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


        //TODO think of a calendar
        binding.previousButton.setOnClickListener {
            viewModel.onPreviousMealSelected()
        }

        binding.nextButton.setOnClickListener {
            viewModel.onNextMealSelected()
        }

        viewModel.mealUi.observe(viewLifecycleOwner, Observer {
            if (it is Lce.Success) {
                bindUi(it.data)
            }
        })
    }

    private fun bindUi(data: MealUi) {
        val color = ContextCompat.getColor(requireContext(), data.backgroundColorRes)
        binding.root.setBackgroundColor(color)

        updateExternalColors(color, data.navigationColorRes)

        binding.title.setText(data.titleRes)
        binding.backgroundImage.setImageResource(data.backgroundImageRes)

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