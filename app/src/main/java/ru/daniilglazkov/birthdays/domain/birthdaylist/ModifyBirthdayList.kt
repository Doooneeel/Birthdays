package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.TransformBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.TransformBirthdayListFactory
import ru.daniilglazkov.birthdays.domain.core.Repository
import ru.daniilglazkov.birthdays.domain.settings.*

/**
 * @author Danil Glazkov on 05.03.2023, 16:07
 */
interface ModifyBirthdayList {

    suspend fun modify(source: BirthdayListDomain): BirthdayListDomain


    class Base(
        private val fetchSettings: Repository.Read<SettingsDomain>,
        private val handleSettingsDataRequest: HandleSettingsDataRequest,
        private val transformMapper: TransformBirthdayListFactory
    ) : ModifyBirthdayList {
        override suspend fun modify(source: BirthdayListDomain): BirthdayListDomain {
            val settings: SettingsDomain = handleSettingsDataRequest.handle {
                fetchSettings.read()
            }
            val transformBirthdayList: TransformBirthdayList = settings.map(transformMapper)

            return transformBirthdayList.transform(source)
        }
    }
}