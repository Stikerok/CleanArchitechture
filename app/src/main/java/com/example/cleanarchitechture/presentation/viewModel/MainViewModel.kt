package com.example.cleanarchitechture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val calculateUseCase: CalculateUseCase by lazy { Dependencies.getCalculateUseCase() }
    private val operationsUseCase: OperationsUseCase by lazy { Dependencies.getOperationsUseCase() }
    private val personsUseCase: PersonsUseCase by lazy { Dependencies.getPersonUseCase() }

    var first: String = ""
    var second: String = ""

    private val operations = MutableLiveData<List<Operation>>(listOf())
    private val _calculationState = MutableLiveData<CalculationState>(CalculationState.Free)
    val calculationState: LiveData<CalculationState> = _calculationState

    fun getOperations(): LiveData<List<Operation>> {
        return operations
    }

    suspend fun setFree() {
        delay(3000)
        _calculationState.value = CalculationState.Free
    }

    fun onOperationSelected (operation: Operation) {
        viewModelScope.launch {
            operationsUseCase.deleteOperation(operation)
        }
    }

    fun calculate(): Int {
        var result = 0
        _calculationState.value = CalculationState.Loading
        viewModelScope.launch {
            result = calculateUseCase.calculate(first.toInt(), second.toInt())
            _calculationState.value = CalculationState.Result
            setFree()
        }
        return result
    }

    init {
        viewModelScope.launch {
            operationsUseCase.getOperations().collect {
                operations.value = it
            }
            personsUseCase.getPersons().collect{
                Log.d("data",it.toString())
            }
        }

    }
}