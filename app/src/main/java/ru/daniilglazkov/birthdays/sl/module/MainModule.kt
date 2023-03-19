package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.sl.core.*
import ru.daniilglazkov.birthdays.ui.main.MainViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 22:17
 */
class MainModule(
    private val coreModule: CoreModule,
    private val serviceModule: ServiceModule,
) : Module<MainViewModel.Base> {
    override fun viewModel() = MainViewModel.Base(
        serviceModule.provideReceiverWrapper(),
        coreModule.navigation()
    )
}