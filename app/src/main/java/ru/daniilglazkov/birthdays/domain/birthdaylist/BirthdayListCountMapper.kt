package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain

/**
 * @author Danil Glazkov on 09.10.2022, 22:25
 */
interface BirthdayListCountMapper : BirthdayListDomain.Mapper<Int> {

    abstract class Abstract(private val predicate: BirthdayCheckMapper) : BirthdayListCountMapper {
        override fun map(list: List<BirthdayDomain>): Int = list.count { birthdayDomain ->
            birthdayDomain.map(predicate)
        }
    }

    class Base(predicate: BirthdayCheckMapper) : Abstract(predicate)

    class CountWithoutHeaders : Abstract(BirthdayCheckMapper.IsNotHeader)
}