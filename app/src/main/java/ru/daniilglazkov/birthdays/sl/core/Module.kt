package ru.daniilglazkov.birthdays.sl.core

import androidx.lifecycle.ViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 03:17
 */
interface Module<VM : ViewModel> {
    fun viewModel(): VM
}