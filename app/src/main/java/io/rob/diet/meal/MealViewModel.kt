package io.rob.diet.meal

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.common.Lce
import kotlinx.coroutines.launch

class MealViewModel @ViewModelInject constructor(
    private val mealDecider: MealDecider,
    private val dayDecider: DayDecider,
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _mealUi = MutableLiveData<Lce<MealUi>>()

    val mealUi: LiveData<Lce<MealUi>>
        get() = _mealUi

    fun fetchNextMeal() {
        fetchMealWithParameters(
            mealDecider.mealForTime(),
            dayDecider.getCurrentDayOfTheWeek()
        )
    }

    private fun fetchMealWithParameters(meal: Meal, day: Int) {
        _mealUi.postValue(Lce.Loading)

        viewModelScope.launch {
            val items = mealRepository.fetchDataForMealAndDay(meal, day)
            val ui = wrapIntoMealUi(meal, items)
            _mealUi.postValue(Lce.Success(ui))
        }
    }

    private fun wrapIntoMealUi(meal: Meal, items: List<FoodElement>): MealUi {
        val staticData = MealUiStaticData.from(meal)

        return MealUi(
            titleRes = meal.stringResId,
            backgroundColorRes = staticData.backgroundColorRes,
            backgroundImageRes = staticData.iconRes,
            navigationColorRes = staticData.navigationColorRes,
            elements = items
        )
    }
}