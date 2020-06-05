package io.rob.diet.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.common.Lce
import io.rob.diet.databinding.FragmentCurrentMealBinding

@AndroidEntryPoint
class CurrentMealFragment : Fragment() {

    private lateinit var binding: FragmentCurrentMealBinding

    private val viewModel by viewModels<MealViewModel>()

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

        viewModel.mealUi.observe(viewLifecycleOwner, Observer {
            if (it is Lce.Success) {
                bindUi(it.data)
            }
        })
    }

    private fun bindUi(data: MealUi) {
        binding.root.setBackgroundColor(
            ContextCompat.getColor(requireContext(), data.backgroundColorRes)
        )

        binding.title.setText(data.titleRes)
        binding.backgroundImage.setImageResource(data.backgroundImageRes)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchNextMeal()
    }
}