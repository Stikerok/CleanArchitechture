package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

class PersonsUseCaseImpl(
    private val personRepository: PersonRepository
) : PersonsUseCase {
    override fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
    }

    override fun getPersonsRx(): Observable<List<Person>> {
        return personRepository.getPersonsRx()
    }

    override fun addPerson(person: Person) {
        personRepository.addPerson(person)
    }

    override fun deletePerson(person: Person) {
        personRepository.deletePerson(person)
    }
}