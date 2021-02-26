package io.rob.diet.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.rob.diet.common.Lce
import io.rob.diet.progress.ComposeRecapUI
import io.rob.diet.progress.EmptyRecapException
import io.rob.diet.progress.Measurement
import io.rob.diet.progress.MeasurementTransformer
import io.rob.diet.storage.ProgressRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(
    private val repository: ProgressRepository,
    private val measurementTransformer: MeasurementTransformer
) : ViewModel() {

    private val _composeRecap = MutableLiveData<Lce<ComposeRecapUI>>()

    val composeRecap : LiveData<Lce<ComposeRecapUI>>
        get() = _composeRecap

    fun fetchComposeRecap() {
        _composeRecap.postValue(Lce.Loading)

        viewModelScope.launch {
            val measurements = repository.getMeasurements()
            val recapUi = measurementTransformer.toComposeRecapUI(measurements)

            if (recapUi.isEmpty()) {
                _composeRecap.postValue(Lce.Error(EmptyRecapException()))
            } else {
                _composeRecap.postValue(Lce.Success(recapUi))
            }
        }
    }

    fun saveData(measurement : Measurement) {
        viewModelScope.launch {
            repository.insertNewMeasurement(measurement)
        }
    }
}