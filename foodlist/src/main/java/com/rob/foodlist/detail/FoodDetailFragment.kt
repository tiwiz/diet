package com.rob.foodlist.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.rob.diet.common.Lce
import io.rob.diet.databinding.FragmentFoodDetailBinding

@AndroidEntryPoint
class FoodDetailFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<FoodDetailViewModel>()

    private val detailAdapter by lazy {
        FoodDetailAdapter(LayoutInflater.from(requireActivity()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFoodDetailBinding.inflate(inflater, container, false)

        binding.foodDetail.layoutManager = LinearLayoutManager(requireActivity())
        binding.foodDetail.adapter = detailAdapter

        return binding.detailRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.elements.observe(viewLifecycleOwner, Observer {
            if (it is Lce.Success) {
                detailAdapter.updateElements(it.data)
            }
        })

        val query = FoodDetailFragmentArgs.fromBundle(requireArguments()).foodType
        viewModel.fetchFoodDetails(query)
    }

}
