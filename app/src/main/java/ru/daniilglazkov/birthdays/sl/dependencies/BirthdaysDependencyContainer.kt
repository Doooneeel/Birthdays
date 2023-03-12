package ru.daniilglazkov.birthdays.sl.dependencies

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.data.birthdaylist.BaseBirthdayListRepository
import ru.daniilglazkov.birthdays.data.birthdaylist.BirthdayDomainToDataMapper
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.*
import ru.daniilglazkov.birthdays.data.core.BaseFirstLaunch
import ru.daniilglazkov.birthdays.data.core.cache.BirthdaysDatabase
import ru.daniilglazkov.birthdays.data.core.cache.PreferencesDataStore
import ru.daniilglazkov.birthdays.data.settings.*
import ru.daniilglazkov.birthdays.data.settings.cache.SettingsCacheDataSource
import ru.daniilglazkov.birthdays.data.settings.cache.SettingsDataToCacheMapper
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortModeList
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.DependencyContainer
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.sl.module.*
import ru.daniilglazkov.birthdays.ui.birthday.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListViewModel
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayViewModel
import ru.daniilglazkov.birthdays.ui.settings.SettingsViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 03:24
 */
class BirthdaysDependencyContainer(
    private val coreModule: CoreModule,
    cacheModule: CacheModule,
    private val dateModule: DateModule,
    private val zodiacModule: ZodiacModule,
    private val next: DependencyContainer = DependencyContainer.Error()
) : DependencyContainer {
    private val database: BirthdaysDatabase = cacheModule.provideDatabase()
    private val preferences: SharedPreferences = cacheModule.preferences(FILE_NAME)

    private val birthdaysRepository = BaseBirthdayListRepository(
        BirthdayListCacheDataSource.Base(
            database.provideBirthdaysDao(),
            BirthdayDataToCacheMapper.Base()
        ),
        BirthdayDataToDomainMapper.Base(),
        BirthdayDomainToDataMapper.Base(),
        BaseFirstLaunch(PreferencesDataStore.Boolean(preferences), FIRST_LAUNCH_KEY)
    )

    private val settingsRepository = BaseSettingsRepository(
        SettingsCacheDataSource.Base(
            database.provideSettingsDao(),
            SettingsDataToCacheMapper.Base()
        ),
        SettingsDomainToDataMapper.Base(),
        SettingsDataToDomainMapper.Base(SortModeList.Base())
    )

    override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> = when (clazz) {
        BirthdayListViewModel.Base::class.java -> BirthdayListModule(
            coreModule,
            dateModule,
            zodiacModule,
            birthdaysRepository,
            settingsRepository,
        )
        BirthdayViewModel.Base::class.java -> BirthdayModule(
            coreModule,
            dateModule,
            zodiacModule,
            birthdaysRepository,
        )
        NewBirthdayViewModel.Base::class.java -> NewBirthdayModule(
            coreModule,
            dateModule,
            database.provideNewBirthdayDao(),
            birthdaysRepository
        )
        SettingsViewModel.Base::class.java -> SettingsModule(
            coreModule,
            settingsRepository
        )
        else -> next.module(clazz)
    }

    companion object {
        private const val FILE_NAME = "birthdays_preferences"
        private const val FIRST_LAUNCH_KEY = "birthday_list_first_launch"
    }
}