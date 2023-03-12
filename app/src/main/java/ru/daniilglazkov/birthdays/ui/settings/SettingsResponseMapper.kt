package ru.daniilglazkov.birthdays.ui.settings

import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 01.09.2022, 21:29
 */
interface SettingsResponseMapper : SettingsDomain.Mapper<Unit> {

    class Base(
        private val communication: Communication.Update<SettingsUi>
    ) : SettingsResponseMapper {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean) =
            communication.put(SettingsUi.Base(sort, reverse, group))
    }
}