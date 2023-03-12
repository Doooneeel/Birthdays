package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.core.HandleDataRequest

/**
 * @author Danil Glazkov on 10.06.2022, 01:24
 */
interface HandleBirthdayListDataRequest : HandleDataRequest<BirthdayListResponse> {

    class Base : HandleDataRequest.Abstract<BirthdayListResponse>(), HandleBirthdayListDataRequest {
        override fun handleException(exception: Exception) = BirthdayListResponse.Failure(exception)
    }
}