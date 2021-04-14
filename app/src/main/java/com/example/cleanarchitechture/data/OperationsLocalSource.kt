package com.example.cleanarchitechture.data

import com.example.cleanarchitechture.domain.Operation
import com.example.cleanarchitechture.domain.OperationsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OperationsLocalSource: OperationsRepository{

    private var operations = mutableListOf(Operation(1,2, 3), Operation(3,6,9))

    override suspend fun getOperations(): List<Operation> {
        var sum = 0
        withContext(Dispatchers.IO){
            for (i in 0..Int.MAX_VALUE) {
                if (sum % 2 == 0) {
                    sum +=1
                }else sum -=1
            }
        }
        return operations
    }

    override suspend fun addOperation(operation: Operation) {
        operations.add(operation)
    }

    override suspend fun deleteOperation(operation: Operation) {
        operations.remove(operation)
    }


}