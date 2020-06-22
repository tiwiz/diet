package io.rob.diet.meal

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.storage.RemotePortionsDao
import kotlinx.coroutines.launch

class NewMealViewModel @ViewModelInject constructor(
    private val dao: RemotePortionsDao
) : ViewModel() {

    val foodTypes = FoodType.values()

    private var selectedFoodType: FoodType? = null

    fun onFoodTypeSelected(position: Int) {
        selectedFoodType = foodTypes[position]
    }

    fun isValidFoodType() = selectedFoodType != null

    fun saveRemotePortion(portion: RemoteMealPortion) {
        viewModelScope.launch {
            dao.insertPersonalizedFood(
                portion.copy(type = selectedFoodType!!.value)
            )
        }
    }
}