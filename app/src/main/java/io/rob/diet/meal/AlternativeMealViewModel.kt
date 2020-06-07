package io.rob.diet.meal

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlternativeMealViewModel @ViewModelInject constructor() : ViewModel() {

    private val _alternativeMeal = MutableLiveData<Pair<Meal, Int>>()
    val alternativeMeal : LiveData<Pair<Meal, Int>>
        get() = _alternativeMeal

    fun updateMeal(meal: Meal, day: Int) {
        _alternativeMeal.postValue(meal to day)
    }
}