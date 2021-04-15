package com.example.cleanarchitechture.data

import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.domain.OperationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class OperationsLocalSource: OperationsRepository{

    private val operations = mutableListOf(Operation(1,2, 3),
        Operation(3,6,9))
    private val operationsFlow : MutableSharedFlow<List<Operation>> =
        MutableSharedFlow(replay = 0)
    override suspend fun getOperations() = operationsFlow
        .apply {
        emit(operations)
    }


    override suspend fun addOperation(operation: Operation) {
        operations.add(operation)
        operationsFlow.emit(operations)
    }

    override suspend fun deleteOperation(operation: Operation) {
        operations.remove(operation)
        operationsFlow.emit(operations)
    }


}