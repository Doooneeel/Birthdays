package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayResponse
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.HandleDomainRequest

/**
 * @author Danil Glazkov on 17.02.2023, 19:17
 */
interface HandleBirthdayRequest : HandleDomainRequest<BirthdayResponse> {

    class Base(
        dispatchers: CoroutineDispatchers,
        private val responseMapper: BirthdayResponseMapper
    ) : HandleDomainRequest.Abstract<BirthdayResponse>(
        dispatchers
    ) , HandleBirthdayRequest {
        override fun launchUi(response: BirthdayResponse) =
            response.map(responseMapper)
    }
}