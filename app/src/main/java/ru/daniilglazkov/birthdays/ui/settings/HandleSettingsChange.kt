package ru.daniilglazkov.birthdays.ui.settings

import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.HandleDomainRequest

/**
 * @author Danil Glazkov on 16.02.2023, 20:08
 */
interface HandleSettingsChange : HandleDomainRequest<SettingsDomain> {

    class Base(
        dispatchers: CoroutineDispatchers,
        private val responseMapper: SettingsResponseMapper
    ) : HandleDomainRequest.Abstract<SettingsDomain>(dispatchers), HandleSettingsChange {
        override fun launchUi(response: SettingsDomain) =
            response.map(responseMapper)
    }
}