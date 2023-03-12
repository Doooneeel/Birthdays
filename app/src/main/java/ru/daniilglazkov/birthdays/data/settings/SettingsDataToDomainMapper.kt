package ru.daniilglazkov.birthdays.data.settings

import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortModeList

/**
 * @author Danil Glazkov on 09.10.2022, 19:31
 */
interface SettingsDataToDomainMapper : SettingsData.Mapper<SettingsDomain> {

    class Base(private val sortModeList: SortModeList) : SettingsDataToDomainMapper {
        override fun map(sortModeId: Int, reverse: Boolean, group: Boolean): SettingsDomain =
            SettingsDomain.Base(sortModeList.fetchById(sortModeId), reverse, group)
    }
}

