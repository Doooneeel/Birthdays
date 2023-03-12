package ru.daniilglazkov.birthdays.ui.newbirthday

import kotlinx.coroutines.CoroutineScope
import ru.daniilglazkov.birthdays.domain.newbirthday.AboutBirthdateDomain
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.HandleDomainRequest

/**
 * @author Danil Glazkov on 11.03.2023, 17:01
 */
interface HandleNewBirthdayRequest : HandleDomainRequest<NewBirthdayDomain> {

    fun handleBirthdate(scope: CoroutineScope, block: suspend () -> AboutBirthdateDomain)


    class Base(
        private val communication: NewBirthdayCommunication.Put,
        private val mapperToUi: NewBirthdayDomainToUiMapper,
        private val handleAboutBirthdate: HandleAboutBirthdateRequest,
        dispatchers: CoroutineDispatchers,
    ) : HandleDomainRequest.AbstractWithMapper<NewBirthdayDomain, NewBirthdayUi>(
        dispatchers
    ) , HandleNewBirthdayRequest {

        override fun launchUi(result: NewBirthdayUi) {
            communication.putNewBirthday(result)
        }

        override suspend fun map(response: NewBirthdayDomain): NewBirthdayUi =
            response.map(mapperToUi)

        override fun handleBirthdate(
            scope: CoroutineScope,
            block: suspend () -> AboutBirthdateDomain
        ) = handleAboutBirthdate.handle(scope, block)
    }
}