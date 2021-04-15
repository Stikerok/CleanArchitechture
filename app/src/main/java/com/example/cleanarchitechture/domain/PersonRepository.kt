package com.example.cleanarchitechture.domain

import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): Flow<List<Person>>
}