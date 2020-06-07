package io.rob.diet.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.common.Lce
import io.rob.diet.meal.FoodType
import io.rob.diet.meal.MealPortion
import io.rob.diet.storage.DietDao
import kotlinx.coroutines.launch

class FoodDetailViewModel @ViewModelInject constructor(
    private val dietDao: DietDao
) : ViewModel() {

    private val _elements = MutableLiveData<Lce<List<DetailElement>>>()

    val elements: LiveData<Lce<List<DetailElement>>>
        get() = _elements

    fun fetchFoodDetails(query: String) {
        viewModelScope.launch {
            val details = dietDao.getFoodAlternativesByType(FoodType.from(query))
                .map { it.toDetailElement() }

            _elements.postValue(Lce.Success(details))
        }
    }

    private fun MealPortion.toDetailElement() : DetailElement {
        val cleanWeight = when {
            unit == null -> "$weight"
            unit.length < 3 -> "$weight$unit"
            else -> "$weight $unit"
        }
        return DetailElement(weight = cleanWeight, description = definition)
    }

}