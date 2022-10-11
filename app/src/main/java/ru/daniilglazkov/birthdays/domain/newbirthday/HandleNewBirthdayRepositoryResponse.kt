package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.core.HandleRepositoryResponse
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.10.2022, 17:29
 */
interface HandleNewBirthdayRepositoryResponse : HandleRepositoryResponse<NewBirthdayDomain> {

    class Base(now: LocalDate) : HandleRepositoryResponse.Abstract<NewBirthdayDomain>(
        NewBirthdayDomain.Empty(now)
    ) , HandleNewBirthdayRepositoryResponse
}