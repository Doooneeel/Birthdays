package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author Danil Glazkov on 10.06.2022, 01:18
 */
interface ProvideViewModel {
    fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T
}