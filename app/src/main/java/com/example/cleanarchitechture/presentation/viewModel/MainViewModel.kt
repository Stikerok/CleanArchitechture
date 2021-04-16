package com.example.cleanarchitechture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitechture.Dependencies
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val personsUseCase: PersonsUseCase by lazy { Dependencies.getPersonUseCase() }

    var name: String = ""
    var rating: String = ""

    private val persons = MutableLiveData<List<Person>>(listOf())
    private val _calculationState = MutableLiveData<CalculationState>(CalculationState.Free)
    val calculationState: LiveData<CalculationState> = _calculationState

    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    suspend fun setFree() {
        delay(3000)
        _calculationState.value = CalculationState.Free
    }

    fun onOperationSelected(person: Person) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                personsUseCase.deletePerson(person)
            }
        }
    }

    fun addPerson() {
        val rating = this.rating.toInt()
        val person = Person(name, rating)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                personsUseCase.addPerson(person)
            }
        }
    }

    init {
        viewModelScope.launch {
            personsUseCase.getPersons().collect {
                persons.value = it
            }
        }

    }
}