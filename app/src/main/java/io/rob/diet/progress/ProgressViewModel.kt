package io.rob.diet.progress

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.rob.diet.common.Lce
import io.rob.diet.storage.ProgressRepository
import kotlinx.coroutines.launch

class ProgressViewModel @ViewModelInject constructor(
    private val repository: ProgressRepository,
    private val measurementTransformer: MeasurementTransformer
) : ViewModel() {

    private val _recap = MutableLiveData<Lce<RecapUI>>()

    val recap: LiveData<Lce<RecapUI>>
        get() = _recap

    fun fetchRecap() {
        _recap.postValue(Lce.Loading)

        viewModelScope.launch {
            val measurements = repository.getMeasurements()
            val recapUi = measurementTransformer.toRecapUI(measurements)

            if (recapUi == null) {
                _recap.postValue(Lce.Error(EmptyRecapException()))
            } else {
                _recap.postValue(Lce.Success(recapUi))
            }
        }
    }

}


