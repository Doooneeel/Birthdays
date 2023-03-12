package ru.daniilglazkov.birthdays.domain.settings

import ru.daniilglazkov.birthdays.domain.core.Repository
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 04:49
 */
interface SettingsInteractor : FetchSettings {

    suspend fun changeSortMode(sort: SortMode): SettingsDomain

    suspend fun change(reverse: Boolean, group: Boolean): SettingsDomain

    suspend fun saveChanges()


    class Base(
        private val repository: Repository.Mutable<SettingsDomain>,
        private val handleRequest: HandleSettingsDataRequest,
        private val changeSettings: ChangeSettings
    ) : SettingsInteractor {

        override suspend fun fetchSettings(): SettingsDomain {
            val settings = handleRequest.handle { repository.read() }
            return changeSettings.change { settings }
        }

        override suspend fun changeSortMode(sort: SortMode): SettingsDomain =
            changeSettings.change { previous: SettingsDomain -> previous.changeSortMode(sort) }

        override suspend fun change(reverse: Boolean, group: Boolean): SettingsDomain =
            changeSettings.change { previous: SettingsDomain -> previous.change(reverse, group) }

        override suspend fun saveChanges() = repository.save(changeSettings.changedValue())
    }
}

