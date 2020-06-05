package io.rob.diet.meal

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.common.Lce
import kotlinx.coroutines.launch

class MealViewModel @ViewModelInject constructor(
    mealDecider: MealDecider,
    dayDecider: DayDecider,
    private val mealRepository: MealRepository,
    private val alternativeMealDecider: AlternativeMealDecider
) : ViewModel() {

    private val _mealUi = MutableLiveData<Lce<MealUi>>()

    private var currentMeal = mealDecider.mealForTime()

    private var currentDay = dayDecider.getCurrentDayOfTheWeek()

    val mealUi: LiveData<Lce<MealUi>>
        get() = _mealUi

    fun fetchNextMeal() {
        fetchMealWithParameters(currentMeal, currentDay)
    }

    fun onPreviousMealSelected() {
        val (meal, day) = alternativeMealDecider.previousMealFrom(currentMeal, currentDay)

        currentMeal = meal
        currentDay = day

        fetchNextMeal()
    }

    fun onNextMealSelected() {
        val (meal, day) = alternativeMealDecider.nextMealFrom(currentMeal, currentDay)

        currentMeal = meal
        currentDay = day

        fetchNextMeal()
    }

    private fun fetchMealWithParameters(meal: Meal, day: Int) {
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