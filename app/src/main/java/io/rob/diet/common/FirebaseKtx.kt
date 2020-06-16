package io.rob.diet.common

import com.google.firebase.auth.FirebaseAuth
import io.rob.diet.settings.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlin.coroutines.resume

fun user(): User? =
    FirebaseAuth.getInstance().currentUser?.let {
        User(displayName = it.displayName, photoUrl = it.photoUrl, id = it.uid)
    }

fun userOrEmpty() = user() ?: User.empty

suspend fun Query.await(): DataSnapshot = suspendCancellableCoroutine { cont ->
    keepSynced(true)
    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            cont.cancel(p0.toException())
        }

        override fun onDataChange(p0: DataSnapshot) {
            cont.resume(p0)
        }
    })
}

@ExperimentalCoroutinesApi
fun Query.asFlow(): Flow<DataSnapshot> = callbackFlow {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            // try {
            sendBlocking(snapshot)
            // } catch (e: Exception) {
            //     // Handle exception from the channel: failure in flow or premature closing
            // }
        }

        override fun onCancelled(p0: DatabaseError) {
            cancel(CancellationException("Query ${ref}. onCancelled()", p0.toException()))
        }
    }
    addValueEventListener(listener)
    awaitClose { removeEventListener(listener) }
}

@FlowPreview
@ExperimentalCoroutinesApi
inline fun <reified T> Query.asFlowOfList() = callbackFlow<Collection<T>> {
    val listener = object : ChildEventListener {
        val map = mutableMapOf<String, T>()
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            map[snapshot.key!!] = snapshot.getValue<T>()!!
            sendBlocking(map.values)
        }

        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            map[snapshot.key!!] = snapshot.getValue<T>()!!
            sendBlocking(map.values)
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            map.remove(snapshot.key!!)
            sendBlocking(map.values)
        }

        override fun onCancelled(error: DatabaseError) {
            cancel(CancellationException("Query ${ref}. onCancelled()", error.toException()))
        }
    }
    addChildEventListener(listener)
    awaitClose {
        removeEventListener(listener)
    }
}.debounce(5)