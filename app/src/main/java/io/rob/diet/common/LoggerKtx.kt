package io.rob.diet.common

import com.google.android.gms.tasks.Task
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

fun log(e: Exception) {
    FirebaseCrashlytics.getInstance().recordException(e)
    Timber.e(e)
}

fun Task<*>.logFailure() = addOnFailureListener { log(it) }