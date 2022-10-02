package ru.daniilglazkov.birthdays.sl.dependencies

import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.DependencyContainer
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.sl.module.MainModule
import ru.daniilglazkov.birthdays.ui.main.MainViewModel

/**
 * @author Danil Glazkov on 25.08.2022, 11:44
 */
class MainDependencyContainer(
    private val coreModule: CoreModule,
    private val next: DependencyContainer = DependencyContainer.Error()
) : DependencyContainer {
    override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> {
        return if (clazz == MainViewModel::class.java) {
            MainModule(coreModule.navigation())
        } else {
            next.module(clazz)
        }
    }
}