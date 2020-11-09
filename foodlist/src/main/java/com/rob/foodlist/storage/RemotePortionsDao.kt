package com.rob.foodlist.storage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import io.rob.diet.common.await
import io.rob.diet.common.logFailure
import javax.inject.Inject

class RemotePortionsDao @Inject constructor() {

    private val db: DatabaseReference
        get() = Firebase.database.getReference(DB_REFERENCE)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    suspend fun getPersonalizedPortions(foodType: com.rob.foodlist.meal.FoodType): List<com.rob.foodlist.detail.DetailElement> =
        remotePortions().filter { it.type!! == foodType.value  }
            .map { it.toDetailElement() }

    private suspend fun remotePortions() =
        db.await().getValue<List<com.rob.foodlist.meal.RemoteMealPortion>>().orEmpty()

    suspend fun insertPersonalizedFood(portion: com.rob.foodlist.meal.RemoteMealPortion) {
        val portions = remotePortions() + portion
        db.setValue(portions).logFailure()
    }

    private fun com.rob.foodlist.meal.RemoteMealPortion.toDetailElement(): com.rob.foodlist.detail.DetailElement {
        val cleanWeight = when {
            unit == null -> "$weight"
            unit.length < 3 -> "$weight$unit"
            else -> "$weight $unit"
        }
        return com.rob.foodlist.detail.DetailElement(
            weight = cleanWeight,
            description = definition!!
        )
    }

    companion object {
        private const val DB_REFERENCE = "portions"
    }
}
