package com.example.cleanarchitechture.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitechture.domain.*
import com.example.cleanarchitechture.entity.Person
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val personsUseCase: PersonsUseCase
) : ViewModel() {

//    private val personsUseCase: PersonsUseCase by lazy { Dependencies.getPersonUseCase() }

    var name: String = ""
    var rating: String = ""

    private val persons = MutableLiveData<List<Person>>(listOf())
    private val personsFilter = MutableLiveData<List<Person>>(listOf())
    private val _calculationState = MutableLiveData<CalculationState>(CalculationState.Free)
    private var compositeDisposable = CompositeDisposable()

    fun getPersons(): LiveData<List<Person>> {
        return persons
    }

    fun getPersonsFilter(): LiveData<List<Person>> {
        return personsFilter
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
//        viewModelScope.launch {
//            personsUseCase.getPersons().collect {
//                persons.value = it
//            }
//        }
        val subscribe = personsUseCase.getPersonsRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                persons.value = it
            }
        val subscribeFilter = personsUseCase.getPersonsRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                Log.d("THREAD", Thread.currentThread().name)
                it.size < 3
            }
            .subscribe {
                personsFilter.value = it
            }
        compositeDisposable.addAll(subscribeFilter,subscribe)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}