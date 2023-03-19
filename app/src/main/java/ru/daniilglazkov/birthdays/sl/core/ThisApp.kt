package ru.daniilglazkov.birthdays.sl.core

import android.app.Application
import androidx.lifecycle.*
import ru.daniilglazkov.birthdays.BuildConfig
import ru.daniilglazkov.birthdays.service.core.ProvideReceiverWrapper
import ru.daniilglazkov.birthdays.sl.dependencies.BirthdaysDependencyContainer
import ru.daniilglazkov.birthdays.sl.dependencies.MainDependencyContainer
import ru.daniilglazkov.birthdays.sl.module.ServiceModule
import ru.daniilglazkov.birthdays.sl.module.datetime.DateTimeModule
import ru.daniilglazkov.birthdays.sl.module.zodiac.ZodiacModule
import ru.daniilglazkov.birthdays.sl.module.cache.ProvideBirthdayListRepository
import ru.daniilglazkov.birthdays.ui.core.ProvideViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 01:59
 */
class ThisApp : Application(), ProvideViewModel, ProvideBirthdayListRepository,
    ProvideNotificationMapper, ProvideReceiverWrapper
{
    private lateinit var viewModelsFactory: ViewModelsFactory
    private lateinit var birthdaysDependencyContainer: BirthdaysDependencyContainer
    private lateinit var serviceModule: ServiceModule

    override fun onCreate() {
        super.onCreate()

        val provideInstances = if (BuildConfig.DEBUG)
            ProvideInstances.Debug(applicationContext)
        else
            ProvideInstances.Release(applicationContext)

        val coreModule = CoreModule.Base(applicationContext)
        val dateTimeModule = DateTimeModule.Base()
        val zodiacModule = ZodiacModule.Base(coreModule.manageResources())

        serviceModule = ServiceModule.Base(applicationContext, dateTimeModule)
        serviceModule.createNotificationChannels()

        birthdaysDependencyContainer = BirthdaysDependencyContainer(
            coreModule = coreModule,
            cacheModule = provideInstances.provideCacheModule(),
            dateTimeModule = dateTimeModule,
            zodiacModule = zodiacModule
        )

        val dependencyContainer = MainDependencyContainer(
            coreModule,
            serviceModule,
            birthdaysDependencyContainer
        )

        viewModelsFactory = ViewModelsFactory(dependencyContainer)
    }

    override fun provideReceiverWrapper() = serviceModule.provideReceiverWrapper()

    override fun provideNotificationMapper() = birthdaysDependencyContainer
        .provideNotificationMapper()

    override fun provideBirthdaysRepository() = birthdaysDependencyContainer
        .provideBirthdaysRepository()

    override fun <VM : ViewModel> provideViewModel(clazz: Class<VM>, owner: ViewModelStoreOwner) =
        ViewModelProvider(owner, viewModelsFactory)[clazz]
}