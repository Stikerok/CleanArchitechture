package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.flow.Flow

class PersonsUseCaseImpl (
    val personRepository: PersonRepository
) : PersonsUseCase {
    override fun getPersons(): Flow<List<Person>> {
        return personRepository.getPersons()
    }
}