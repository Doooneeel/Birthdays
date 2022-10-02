package ru.daniilglazkov.birthdays.domain.birthdays.showmode.group

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 22.09.2022, 21:31
 */
interface BirthdayHeader : BirthdayDomain {
    class Base(name: String) : BirthdayDomain.Abstract(HEADER_ID, name, LocalDate.MIN), BirthdayHeader {
        override val type: BirthdayType = BirthdayType.Header
    }

    companion object {
        private const val HEADER_ID: Int = -1
    }

}