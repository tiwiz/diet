package io.rob.diet.storage

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import io.rob.diet.common.await
import io.rob.diet.detail.DetailElement
import io.rob.diet.meal.FoodType
import io.rob.diet.meal.RemoteMealPortion
import javax.inject.Inject

class RemotePortionsDao @Inject constructor() {

    private val db: DatabaseReference
        get() = Firebase.database.getReference(DB_REFERENCE)
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

    suspend fun getPersonalizedPortions(foodType: FoodType): List<DetailElement> =
        remotePortions().filter { it.type!! == foodType.value  }
            .map { it.toDetailElement() }

    private suspend fun remotePortions() =
        db.await().getValue<List<RemoteMealPortion>>().orEmpty()

    suspend fun insertPersonalizedFood(portion: RemoteMealPortion) {
        val portions = remotePortions() + portion
        db.setValue(portions)
    }

    private fun RemoteMealPortion.toDetailElement(): DetailElement {
        val cleanWeight = when {
            unit == null -> "$weight"
            unit.length < 3 -> "$weight$unit"
            else -> "$weight $unit"
        }
        return DetailElement(weight = cleanWeight, description = definition!!)
    }

    companion object {
        private const val DB_REFERENCE = "portions"
    }
}