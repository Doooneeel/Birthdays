package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.core.HandleDataRequest

/**
 * @author Danil Glazkov on 17.02.2023, 19:19
 */
interface HandleBirthdayDataRequest : HandleDataRequest<BirthdayResponse> {

    class Base : HandleDataRequest.Abstract<BirthdayResponse>(), HandleBirthdayDataRequest {
        override fun handleException(exception: Exception) = BirthdayResponse.Failure(exception)
    }
}