package ru.daniilglazkov.birthdays.data.settings

import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 01.09.2022, 21:31
 */
interface SettingsDomainToDataMapper : SettingsDomain.Mapper<SettingsData> {

    class Base : SettingsDomainToDataMapper {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): SettingsData =
            SettingsData.Base(sort.id(), reverse, group)
    }
}
