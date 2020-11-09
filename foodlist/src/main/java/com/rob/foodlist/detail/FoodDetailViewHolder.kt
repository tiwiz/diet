package com.rob.foodlist.detail

import androidx.recyclerview.widget.RecyclerView
import io.rob.diet.databinding.FoodDetailElementBinding

class FoodDetailViewHolder(private val binding: FoodDetailElementBinding) :
    RecyclerView.ViewHolder(binding.elementRoot) {

    fun bindTo(element: DetailElement) {
        binding.weight.text = element.weight
        binding.description.text = element.description
    }
}
