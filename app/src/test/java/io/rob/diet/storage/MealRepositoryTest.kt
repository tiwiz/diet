package io.rob.diet.storage

import assertk.assertThat
import com.nhaarman.mockitokotlin2.mock
import io.rob.diet.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


@ExperimentalCoroutinesApi
class MealRepositoryTest {

    private val lunchProteinPerDay = com.rob.foodlist.meal.PortionPerDay(
        id = 0,
        day = 1,
        protein = com.rob.foodlist.meal.Protein.COLD_CUTS,
        meal = com.rob.foodlist.meal.Meal.LUNCH
    )

    private val dinnerProteinPerDay = com.rob.foodlist.meal.PortionPerDay(
        id = 1,
        day = 1,
        protein = com.rob.foodlist.meal.Protein.PIZZA,
        meal = com.rob.foodlist.meal.Meal.DINNER
    )

    private val breakfastSnack1 = com.rob.foodlist.meal.SnackPortion(
        id = 0,
        definition = BREAKFAST_SNACK_1,
        weight = 100,
        unit = "g",
        meal = com.rob.foodlist.meal.Meal.BREAKFAST,
        group = 1,
        type = com.rob.foodlist.meal.FoodType.VEGGIES
    )

    private val breakfastSnack2 = com.rob.foodlist.meal.SnackPortion(
        id = 1,
        definition = BREAKFAST_SNACK_2,
        weight = 6,
        unit = null,
        meal = com.rob.foodlist.meal.Meal.BREAKFAST,
        group = 2,
        type = com.rob.foodlist.meal.FoodType.VEGGIES
    )

    private val afternoonSnack = com.rob.foodlist.meal.SnackPortion(
        id = 2,
        definition = AFTERNOON_SNACK,
        weight = 6,
        unit = null,
        meal = com.rob.foodlist.meal.Meal.AFTERNOON_SNACK,
        group = 2,
        type = com.rob.foodlist.meal.FoodType.VEGGIES
    )

    private val mockDao: com.rob.foodlist.storage.DietDao = mock {
        runBlocking {
            on(it.getPortionByMealAndDay(com.rob.foodlist.meal.Meal.LUNCH, 1)) doReturn lunchProteinPerDay
            on(it.getPortionByMealAndDay(com.rob.foodlist.meal.Meal.DINNER, 1)) doReturn dinnerProteinPerDay
            on(it.getSnackPortionsByMeal(com.rob.foodlist.meal.Meal.BREAKFAST)) doReturn listOf(
                breakfastSnack1,
                breakfastSnack2
            )
            on(it.getSnackPortionsByMeal(com.rob.foodlist.meal.Meal.AFTERNOON_SNACK)) doReturn listOf(afternoonSnack)
        }
    }

    private val repository = com.rob.foodlist.meal.MealRepository(mockDao)

    @Test
    fun `verify usual main course corresponds of 4 pieces`() = runBlockingTest {
        val uiData = repository.fetchDataForMealAndDay(com.rob.foodlist.meal.Meal.LUNCH, 1)

        assertThat(uiData.size).isEqualTo(4)
        assertThat(uiData[0].typeDetail).isEqualTo("carbs")
        assertThat(uiData[1].typeDetail).isEqualTo("cold_cuts")
        assertThat(uiData[2].typeDetail).isEqualTo("veggies")
        assertThat(uiData[3].typeDetail).isEqualTo("fruit")
    }

    @Test
    fun `verify free main course only has pizza and fruit`() = runBlockingTest {
        val uiData = repository.fetchDataForMealAndDay(com.rob.foodlist.meal.Meal.DINNER, 1)

        assertThat(uiData.size).isEqualTo(2)
        assertThat(uiData[0].typeDetail).isEqualTo("pizza")
        assertThat(uiData[1].typeDetail).isEqualTo("fruit")
    }

    @Test
    fun `verify breakfast items have icons associated`() = runBlockingTest {
        val uiData = repository.fetchDataForMealAndDay(com.rob.foodlist.meal.Meal.BREAKFAST, 1)

        val item1 = "$BREAKFAST_SNACK_1 (100 g)"
        val item2 = "$BREAKFAST_SNACK_2 (6)"

        val icons = uiData.mapNotNull { it.iconRes }
        val texts = uiData.map { it.definition }

        assertThat(icons.size).isEqualTo(uiData.size)
        assertThat(icons).containsAll(R.drawable.ic_food, R.drawable.ic_drink)
        assertThat(texts).containsAll(item1, item2)
    }

    @Test
    fun `verify snacks items have no icons`() = runBlockingTest {
        val uiData = repository.fetchDataForMealAndDay(com.rob.foodlist.meal.Meal.AFTERNOON_SNACK, 1)

        assertThat(uiData.all { it.iconRes == null }).isTrue()
    }

    companion object {
        private const val BREAKFAST_SNACK_1 = "milk"
        private const val BREAKFAST_SNACK_2 = "cookies"
        private const val AFTERNOON_SNACK = "chocolate"
    }
}
