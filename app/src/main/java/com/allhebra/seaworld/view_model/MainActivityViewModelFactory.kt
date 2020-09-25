package com.allhebra.seaworld.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allhebra.seaworld.data.WorldRepository

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
    private val worldRepository: WorldRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(worldRepository) as T
    }
}