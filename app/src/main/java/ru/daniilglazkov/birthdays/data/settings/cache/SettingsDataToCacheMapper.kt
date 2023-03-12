package ru.daniilglazkov.birthdays.data.settings.cache

import ru.daniilglazkov.birthdays.data.settings.SettingsData

/**
 * @author Danil Glazkov on 09.10.2022, 19:36
 */
interface SettingsDataToCacheMapper : SettingsData.Mapper<SettingsCache> {

    class Base : SettingsDataToCacheMapper {
        override fun map(sortModeId: Int, reverse: Boolean, group: Boolean): SettingsCache =
            SettingsCache(sortModeId, reverse, group)
    }
}