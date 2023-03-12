package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.core.HandleDataRequest
import java.time.LocalDate

/**
 * @author Danil Glazkov on 17.02.2023, 19:55
 */
interface HandleNewBirthdayDataRequest : HandleDataRequest<NewBirthdayDomain> {

    class Base(
        private val now: LocalDate,
    ) : HandleDataRequest.Abstract<NewBirthdayDomain>(), HandleNewBirthdayDataRequest {
        override fun handleException(exception: Exception) =
            NewBirthdayDomain.Empty(now)
    }
}