package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain

/**
 * @author Danil Glazkov on 05.09.2022, 17:11
 */
interface NewBirthdayDomainToDataMapper : NewBirthdayDomain.Mapper<NewBirthdayData> {
    class Base : NewBirthdayDomain.Mapper.Abstract<NewBirthdayData>(NewBirthdayData::Base),
        NewBirthdayDomainToDataMapper
}