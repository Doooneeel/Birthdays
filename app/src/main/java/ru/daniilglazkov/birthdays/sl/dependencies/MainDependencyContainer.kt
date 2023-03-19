package ru.daniilglazkov.birthdays.sl.dependencies

import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.sl.core.*
import ru.daniilglazkov.birthdays.sl.module.MainModule
import ru.daniilglazkov.birthdays.sl.module.ServiceModule
import ru.daniilglazkov.birthdays.ui.main.MainViewModel

/**
 * @author Danil Glazkov on 25.08.2022, 11:44
 */
class MainDependencyContainer(
    private val coreModule: CoreModule,
    private val serviceModule: ServiceModule,
    private val next: DependencyContainer = DependencyContainer.Error()
) : DependencyContainer {
    override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> {
        return if (clazz == MainViewModel.Base::class.java)
            MainModule(coreModule, serviceModule)
        else
            next.module(clazz)
    }
}