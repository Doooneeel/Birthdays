package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListResponse
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.HandleDomainRequest

/**
 * @author Danil Glazkov on 18.02.2023, 19:15
 */
interface HandleBirthdayListRequest : HandleDomainRequest<BirthdayListResponse> {

    class Base(
        dispatchers: CoroutineDispatchers,
        private val responseMapper: BirthdayListResponseMapper
    ) : HandleDomainRequest.Abstract<BirthdayListResponse>(dispatchers), HandleBirthdayListRequest {
        override fun launchUi(response: BirthdayListResponse) =
            response.map(responseMapper)
    }
}