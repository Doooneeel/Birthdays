package ru.daniilglazkov.birthdays.sl.dependencies

import android.content.Context
import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.BuildConfig
import ru.daniilglazkov.birthdays.data.birthdays.BaseBirthdayListRepository
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayDomainToDataMapper
import ru.daniilglazkov.birthdays.data.birthdays.cache.*
import ru.daniilglazkov.birthdays.data.core.cache.BirthdaysDatabase
import ru.daniilglazkov.birthdays.data.main.ProvideBirthdayDatabase
import ru.daniilglazkov.birthdays.data.showmode.*
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeCacheDataSource
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeDataToCacheMapper
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.domain.showmode.HandleShowModeRepositoryResponse
import ru.daniilglazkov.birthdays.domain.showmode.ShowModeInteractor
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.DependencyContainer
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.sl.module.*
import ru.daniilglazkov.birthdays.ui.birthday.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListViewModel
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayViewModel
import ru.daniilglazkov.birthdays.ui.settings.SettingsViewModel
import ru.daniilglazkov.birthdays.ui.zodiac.BaseZodiacDomainList

/**
 * @author Danil Glazkov on 10.06.2022, 03:24
 */
class BirthdaysDependencyContainer(
    private val coreModule: CoreModule,
    context: Context,
    private val next: DependencyContainer = DependencyContainer.Error()
) : DependencyContainer {
    private val now = coreModule.provideCurrentDate()
    private val handlePassedDate = HandlePassedDate.Base(now)
    private val nextEvent = NextEvent.Base(
        IsLeapDay.Base(),
        HandleDate.Leap(handlePassedDate, DetermineLeapDay.Base(), now),
        HandleDate.Base(handlePassedDate, now),
        now
    )

    private val database: BirthdaysDatabase

    init {
        val provideDataBase = if (BuildConfig.DEBUG) {
            ProvideBirthdayDatabase.Debug(context)
        } else {
            ProvideBirthdayDatabase.Release(context, DATABASE_NAME)
        }
        database = provideDataBase.provideDatabase()
    }
    private val zodiacGroupClassification = ZodiacGroupClassification.Base(
        BaseZodiacDomainList(coreModule.resourcesManager())
    )

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
        HandleShowModeRepositoryResponse.Base()
    )
    override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> = when (clazz) {
        BirthdayListViewModel.Base::class.java -> BirthdayListModule(
            coreModule,
            birthdaysRepository,
            zodiacGroupClassification,
            showModeInteractor,
            nextEvent,
            now
        )
        BirthdayViewModel.Base::class.java -> BirthdayModule(
            coreModule,
            zodiacGroupClassification,
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