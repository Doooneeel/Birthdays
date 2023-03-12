package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListResponse
import ru.daniilglazkov.birthdays.domain.birthdaylist.FetchBirthdays
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 06.10.2022, 14:59
 */
interface BirthdaySearchQueryCommunication : Communication.Mutable<CharSequence> {

    interface Put {
        fun putQuery(query: CharSequence)
    }

    suspend fun executeQuery(executor: FetchBirthdays): BirthdayListResponse


    class Base : Communication.Ui<CharSequence>(""), BirthdaySearchQueryCommunication {
        override suspend fun executeQuery(executor: FetchBirthdays): BirthdayListResponse =
            executor.birthdays(query = value)
    }
}