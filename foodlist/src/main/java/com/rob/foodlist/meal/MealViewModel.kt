package com.rob.foodlist.meal

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.common.AuthenticationManager
import io.rob.diet.common.Lce
import kotlinx.coroutines.launch

class MealViewModel @ViewModelInject constructor(
    mealDecider: MealDecider,
    dayDecider: DayDecider,
    private val mealRepository: MealRepository,
    private val authenticationManager: AuthenticationManager
) : ViewModel() {

    private val _mealUi = MutableLiveData<Lce<MealUi>>()

    private var currentMeal = mealDecider.mealForTime()

    private var currentDay = dayDecider.getCurrentDayOfTheWeek()

    val mealUi: LiveData<Lce<MealUi>>
        get() = _mealUi

    fun fetchNextMeal() {
        fetchMealWithParameters(currentMeal, currentDay)
    }

    fun canUserAddMeals() = authenticationManager.isUserAuthenticated()

    fun fetchMealWithParameters(meal: Meal, day: Int) {
        viewModelScope.launch {
            val items = mealRepository.fetchDataForMealAndDay(meal, day)
            val ui = wrapIntoMealUi(meal, day, items)
            _mealUi.postValue(Lce.Success(ui))
        }
    }

    private fun wrapIntoMealUi(meal: Meal, day: Int, items: List<FoodElement>): MealUi {
        val staticData = MealUiStaticData.from(meal)

        return MealUi(
            titleRes = meal.stringResId,
            backgroundColorRes = staticData.backgroundColorRes,
            backgroundImageRes = staticData.iconRes,
            navigationColorRes = staticData.navigationColorRes,
            meal = meal,
            day = day,
            elements = items
        )
    }
}
