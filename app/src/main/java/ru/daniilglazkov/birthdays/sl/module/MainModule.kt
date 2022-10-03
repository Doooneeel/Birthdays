package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.core.Navigation
import ru.daniilglazkov.birthdays.ui.main.MainViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 22:17
 */
class MainModule(private val navigation: Navigation.Mutable) : Module<MainViewModel.Base> {
    override fun viewModel() = MainViewModel.Base(navigation)
}