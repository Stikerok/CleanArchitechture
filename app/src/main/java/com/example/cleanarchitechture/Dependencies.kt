package com.example.cleanarchitechture

import com.example.cleanarchitechture.data.OperationsLocalSource
import com.example.cleanarchitechture.data.SumCalculator
import com.example.cleanarchitechture.domain.*

object Dependencies {

    private val operationsRepository: OperationsRepository by lazy { OperationsLocalSource() }

    fun getCalculateRepository():CalculateRepository{
        return SumCalculator()
    }

    fun getOperaationsRepository():OperationsRepository{
        return operationsRepository
    }

    fun getCalculateUseCase():CalculateUseCase{
        return CalculateUseCaseImp(getCalculateRepository(), getOperaationsRepository())
    }

    fun getOperationsUseCase(): OperationsUseCase{
        return OperationsUseCaseImpl(getOperaationsRepository())
    }

}