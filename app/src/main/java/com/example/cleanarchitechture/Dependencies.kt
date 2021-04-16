package com.example.cleanarchitechture

import android.content.Context
import com.example.cleanarchitechture.data.OperationsLocalSource
import com.example.cleanarchitechture.data.SumCalculator
import com.example.cleanarchitechture.db.LocalDatabaseSource
import com.example.cleanarchitechture.domain.*

object Dependencies {

    private val operationsRepository: OperationsRepository by lazy { OperationsLocalSource() }
    private val personRepository : PersonRepository by lazy { LocalDatabaseSource(App.instance) }

    fun getCalculateRepository():CalculateRepository{
        return SumCalculator()
    }

    fun getOperaationsRepository():OperationsRepository{
        return operationsRepository
    }

    fun getPersonUseCase(context: Context): PersonsUseCase {
        return PersonsUseCaseImpl(getPersonsRepository(context))
    }
    fun getPersonsRepository(context: Context): PersonRepository {
        return personRepository
    }

    fun getCalculateUseCase():CalculateUseCase{
        return CalculateUseCaseImpl(getCalculateRepository(), getOperaationsRepository())
    }

    fun getOperationsUseCase(): OperationsUseCase{
        return OperationsUseCaseImpl(getOperaationsRepository())
    }

}