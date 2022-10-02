package ru.daniilglazkov.birthdays.data.birthdays

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain

/**
 * @author Danil Glazkov on 01.09.2022, 21:40
 */
interface BirthdayDomainToDataMapper : BirthdayDomain.Mapper<BirthdayData> {
    class Base : BirthdayDomain.Mapper.Abstract<BirthdayData>(BirthdayData::Base),
        BirthdayDomainToDataMapper
}