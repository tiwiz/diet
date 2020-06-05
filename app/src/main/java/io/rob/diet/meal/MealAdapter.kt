package io.rob.diet.meal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.rob.diet.databinding.MealElementBinding
import javax.inject.Inject

class MealAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<MealViewHolder>() {

    private val elements: ArrayList<FoodElement> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = MealElementBinding.inflate(layoutInflater, parent, false)
        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bindTo(elements[position], onClick)
    }

    fun updateElements(newElements: List<FoodElement>) {
        elements.clear()
        elements.addAll(newElements)
        notifyDataSetChanged()
    }
}