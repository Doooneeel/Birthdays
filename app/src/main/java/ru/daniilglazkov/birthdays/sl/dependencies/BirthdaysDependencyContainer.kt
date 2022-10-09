package ru.daniilglazkov.birthdays.sl.dependencies

import android.content.Context
import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.BuildConfig
import ru.daniilglazkov.birthdays.data.birthdays.*
import ru.daniilglazkov.birthdays.data.birthdays.cache.*
import ru.daniilglazkov.birthdays.data.core.cache.BirthdaysDatabase
import ru.daniilglazkov.birthdays.data.main.ProvideBirthdayDatabase
import ru.daniilglazkov.birthdays.data.showmode.*
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeCacheDataSource
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeDataToCacheMapper
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.HandleShowModeException
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeInteractor
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.DependencyContainer
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.sl.module.*
import ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.settings.SettingsViewModel
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 03:24
 */
class BirthdaysDependencyContainer(
    private val coreModule: CoreModule,
    context: Context,
    private val next: DependencyContainer = DependencyContainer.Error()
) : DependencyContainer {

    private val now get() = LocalDate.now()
    private val nextEvent = NextEvent.Base(now)
    private val database: BirthdaysDatabase

    init {
        val provideDataBase = if (BuildConfig.DEBUG) {
            ProvideBirthdayDatabase.Debug(context)
        } else {
            ProvideBirthdayDatabase.Release(context, DATABASE_NAME)
        }
        database = provideDataBase.provideDatabase()
    }

    private val birthdaysRepository = BaseBirthdayListRepository(
        BirthdayListCacheDataSource.Base(
            database.provideBirthdaysDao(),
            BirthdayDataToCacheMapper.Base()
        ),
        BirthdayDataToDomainMapper.Base(),
        BirthdayDomainToDataMapper.Base(),
    )

    private val showModeInteractor = ShowModeInteractor.Base(
        BaseShowModeRepository(
            ShowModeCacheDataSource.Base(
                database.provideShowModeDao(),
                ShowModeDataToCacheMapper.Base()
            ),
            ShowModeDomainToDataMapper.Base(),
            ShowModeDataToDomainMapper.Base()
        ),
        HandleShowModeException.Base()
    )
    override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> = when (clazz) {
        BirthdaysViewModel.Base::class.java -> BirthdaysModule(
            coreModule,
            birthdaysRepository,
            showModeInteractor,
            nextEvent,
            now
        )
        BirthdayViewModel.Base::class.java -> BirthdaySheetModule(
            coreModule.resourcesManager(),
            birthdaysRepository,
            nextEvent,
            now
        )
        NewBirthdayViewModel.Base::class.java -> NewBirthdayModule(
            coreModule.resourcesManager(),
            database.provideNewBirthdayDao(),
            birthdaysRepository,
            nextEvent,
            now
        )
        SettingsViewModel.Base::class.java -> BirthdaysSettingsModule(showModeInteractor)
        else -> next.module(clazz)
    }

    companion object {
        private const val DATABASE_NAME = "birthdays-database"
    }
}