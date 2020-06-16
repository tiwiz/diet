package io.rob.diet.measurement

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.progress.Measurement
import io.rob.diet.storage.ProgressRepository
import kotlinx.coroutines.launch

class MeasureViewModel @ViewModelInject constructor(
    private val repository: ProgressRepository
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()

    val status : LiveData<Boolean>
        get() = _status

    fun saveData(measurement : Measurement) {
        viewModelScope.launch {
            repository.insertNewMeasurement(measurement)
            _status.postValue(true)
        }
    }
}