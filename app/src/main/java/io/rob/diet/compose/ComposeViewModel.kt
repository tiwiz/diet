package io.rob.diet.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.rob.diet.Charts
import io.rob.diet.charts.ChartDataFilter
import io.rob.diet.charts.ChartModel
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
    private val measurementTransformer: MeasurementTransformer,
    private val filter: ChartDataFilter
) : ViewModel() {

    private val _composeRecap = MutableLiveData<Lce<ComposeRecapUI>>()

    val composeRecap : LiveData<Lce<ComposeRecapUI>>
        get() = _composeRecap

    private val _chartData = MutableLiveData<Lce<ChartModel>>()

    val chartData : LiveData<Lce<ChartModel>>
        get() = _chartData

    fun fetchComposeRecap() {
        _composeRecap.postValue(Lce.Loading)

        viewModelScope.launch {
            val measurements = repository.getMeasurements()
            val recapUi = measurementTransformer.toComposeRecapUI(measurements)

            _composeRecap.postValue(Lce.Success(recapUi))
        }
    }

    fun saveData(measurement : Measurement) {
        viewModelScope.launch {
            repository.insertNewMeasurement(measurement)
        }
    }

    fun fetchPointsFor(type: Charts) {
        _chartData.postValue(Lce.Loading)

        viewModelScope.launch {
            val measurement = repository.getMeasurements()
            val data = filter.toChartData(type, measurement)

            _chartData.postValue(Lce.Success(data))
        }
    }
}