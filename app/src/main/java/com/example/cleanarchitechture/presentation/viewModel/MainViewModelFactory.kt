package com.example.cleanarchitechture.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchitechture.domain.PersonsUseCase

class MainViewModelFactory (private val personsUseCase: PersonsUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(personsUseCase) as T
        else throw Exception()
    }
}