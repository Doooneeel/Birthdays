package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.Add
import ru.daniilglazkov.birthdays.core.Delete
import ru.daniilglazkov.birthdays.core.ReadWithDefault
import ru.daniilglazkov.birthdays.domain.core.Find

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
interface BirthdayListRepository : Delete, Add<BirthdayDomain>, Find<BirthdayDomain>,
    ReadWithDefault<BirthdayListDomain>