package com.rob.foodlist.meal

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import io.rob.diet.databinding.MealElementBinding

class MealViewHolder(private val binding: MealElementBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindTo(element: FoodElement, onClick: (String) -> Unit) {

        if (element.definition != null) {
            binding.element.text = element.definition
        } else {
            binding.element.setText(element.definitionRes!!)
        }

        if(element.iconRes != null){
            binding.elementType.visibility = VISIBLE
            binding.elementType.setImageResource(element.iconRes)
        } else {
            binding.elementType.visibility = GONE
        }

        if(element.typeDetail != null) {
            binding.detailImage.visibility = VISIBLE
            binding.root.setOnClickListener { onClick(element.typeDetail) }
        } else {
            binding.detailImage.visibility = GONE
        }

    }
}
