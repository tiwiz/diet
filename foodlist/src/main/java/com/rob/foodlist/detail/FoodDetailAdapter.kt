package com.rob.foodlist.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.rob.diet.databinding.FoodDetailElementBinding

class FoodDetailAdapter(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<FoodDetailViewHolder>() {

    private val elements: ArrayList<DetailElement> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDetailViewHolder {
        val binding = FoodDetailElementBinding.inflate(layoutInflater, parent, false)
        return FoodDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: FoodDetailViewHolder, position: Int) {
        holder.bindTo(elements[position])
    }

    fun updateElements(newElements: List<DetailElement>) {
        elements.clear()
        elements.addAll(newElements)
        notifyDataSetChanged()
    }
}
