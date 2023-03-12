package ru.daniilglazkov.birthdays.ui.newbirthday

import ru.daniilglazkov.birthdays.domain.newbirthday.AboutBirthdateDomain
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.HandleDomainRequest
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateUi

/**
 * @author Danil Glazkov on 11.03.2023, 16:58
 */
interface HandleAboutBirthdateRequest : HandleDomainRequest<AboutBirthdateDomain> {

    class Base(
        private val communication: AboutBirthdateCommunication.Put,
        private val mapperToUi: AboutBirthdateDomainToUiMapper,
        dispatchers: CoroutineDispatchers,
    ) : HandleDomainRequest.AbstractWithMapper<AboutBirthdateDomain, AboutBirthdateUi>(
        dispatchers
    ) , HandleAboutBirthdateRequest {

        override fun launchUi(result: AboutBirthdateUi) =
            communication.putAboutBirthdate(result)

        override suspend fun map(response: AboutBirthdateDomain): AboutBirthdateUi =
            response.map(mapperToUi)
    }
}