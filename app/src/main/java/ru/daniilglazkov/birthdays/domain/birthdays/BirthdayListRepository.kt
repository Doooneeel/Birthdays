package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.Add

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
interface BirthdayListRepository : Add<BirthdayDomain> {

    fun find(id: Int): BirthdayDomain
    fun birthdays(): BirthdayListDomain
    fun delete(id: Int)
}