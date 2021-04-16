package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

interface PersonsUseCase {
    fun getPersons(): Flow<List<Person>>
    fun getPersonsRx(): Observable<List<Person>>
    fun addPerson(person: Person)
    fun deletePerson(person: Person)
}