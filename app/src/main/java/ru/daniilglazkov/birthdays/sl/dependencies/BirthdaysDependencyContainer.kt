package ru.daniilglazkov.birthdays.sl.dependencies

import android.content.Context
import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.core.HandleException
import ru.daniilglazkov.birthdays.data.birthdays.BaseBirthdayListRepository
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayData
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayDomainToDataMapper
import ru.daniilglazkov.birthdays.data.birthdays.cache.BirthdayListCacheDataSource
import ru.daniilglazkov.birthdays.data.database.ProvideDatabase
import ru.daniilglazkov.birthdays.data.showmode.BaseShowModeRepository
import ru.daniilglazkov.birthdays.data.showmode.ShowModeData
import ru.daniilglazkov.birthdays.data.showmode.ShowModeDomainToDataMapper
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeInteractor
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.DependencyContainer
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.sl.module.BirthdaySheetModule
import ru.daniilglazkov.birthdays.sl.module.BirthdaysMenuModule
import ru.daniilglazkov.birthdays.sl.module.BirthdaysModule
import ru.daniilglazkov.birthdays.sl.module.NewBirthdayModule
import ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.sheetmenu.MenuSheetViewModel
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

    private val database = ProvideDatabase.Birthdays(context, DATABASE_NAME)
        .provideDatabase()

    private val nextEvent = NextEvent.Base(now)

    private val birthdaysRepository = BaseBirthdayListRepository(
        BirthdayListCacheDataSource.Base(
            database,
            BirthdayData.Mapper.ToDatabaseModel()
        ),
        BirthdayData.Mapper.ToDomain(),
        BirthdayDomainToDataMapper.Base(),
    )

    private val showModeInteractor = BirthdayListShowModeInteractor.Base(
        BaseShowModeRepository(
            ShowModeCacheDataSource.Base(
                database,
                ShowModeData.Mapper.ToDatabaseModel(),
                HandleException.Empty()
            ),
            ShowModeData.Mapper.ToDomain(),
            ShowModeDomainToDataMapper.Base()
        )
    )
    override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> = when (clazz) {
        BirthdaysViewModel.Base::class.java -> {
            BirthdaysModule(coreModule, birthdaysRepository, showModeInteractor, nextEvent, now)
        }
        BirthdayViewModel.Base::class.java -> {
            BirthdaySheetModule(coreModule.resourcesManager(), birthdaysRepository, nextEvent, now)
        }
        NewBirthdayViewModel.Base::class.java -> {
            NewBirthdayModule(coreModule.resourcesManager(), database, birthdaysRepository,
                nextEvent, now
            )
        }
        MenuSheetViewModel.Base::class.java -> {
            BirthdaysMenuModule(showModeInteractor)
        }
        else -> next.module(clazz)
    }

    companion object {
        private const val DATABASE_NAME = "birthdays-database"
    }
}