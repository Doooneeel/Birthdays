package ru.daniilglazkov.birthdays.domain.settings

import ru.daniilglazkov.birthdays.domain.core.HandleDataRequest

/**
 * @author Danil Glazkov on 26.02.2023, 10:14
 */
interface HandleSettingsDataRequest : HandleDataRequest<SettingsDomain> {

    class Base : HandleDataRequest.Abstract<SettingsDomain>(), HandleSettingsDataRequest {
        override fun handleException(exception: Exception) = SettingsDomain.Default
    }
}