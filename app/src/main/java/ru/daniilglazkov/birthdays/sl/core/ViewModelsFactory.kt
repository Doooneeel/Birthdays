package ru.daniilglazkov.birthdays.sl.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author Danil Glazkov on 10.06.2022, 03:32
 */
class ViewModelsFactory(
    private val dependencyContainer: DependencyContainer
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return dependencyContainer.module(modelClass).viewModel() as VM
    }
}