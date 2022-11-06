package ru.daniilglazkov.birthdays.sl.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ru.daniilglazkov.birthdays.sl.dependencies.BirthdaysDependencyContainer
import ru.daniilglazkov.birthdays.sl.dependencies.MainDependencyContainer
import ru.daniilglazkov.birthdays.sl.module.DateModule
import ru.daniilglazkov.birthdays.ui.core.ProvideViewModel
import java.time.LocalDate
import java.time.ZoneId

/**
 * @author Danil Glazkov on 10.06.2022, 01:59
 */
class ThisApp : Application(), ProvideViewModel {
    private lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()

        val coreModule = CoreModule.Base(applicationContext)
        val dateModule = DateModule.Base(LocalDate.now(ZoneId.systemDefault()))

        val dependencyContainer = MainDependencyContainer(
            coreModule = coreModule,
            next = BirthdaysDependencyContainer(coreModule, dateModule, applicationContext)
        )
        viewModelsFactory = ViewModelsFactory(dependencyContainer)
    }

    override fun <VM : ViewModel> provideViewModel(clazz: Class<VM>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, viewModelsFactory)[clazz]
}