package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.*

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
interface BirthdayListRepository : Add.Suspend<BirthdayDomain>, Find.Suspend<BirthdayDomain>,
    Delete.Suspend, FirstLaunch {

    suspend fun birthdays(): BirthdayListDomain

}