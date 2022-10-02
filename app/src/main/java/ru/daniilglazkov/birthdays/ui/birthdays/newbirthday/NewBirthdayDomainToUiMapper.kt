package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain

/**
 * @author Danil Glazkov on 01.09.2022, 21:52
 */
interface NewBirthdayDomainToUiMapper : NewBirthdayDomain.Mapper<NewBirthdayUi> {
    class Base : NewBirthdayDomain.Mapper.Abstract<NewBirthdayUi>(NewBirthdayUi::Base),
        NewBirthdayDomainToUiMapper
}