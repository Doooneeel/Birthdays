package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain

/**
 * @author Danil Glazkov on 11.03.2023, 20:38
 */
interface BirthdayListDomainCompareMapper : BirthdayListDomain.Mapper<Boolean> {

    class CompareObject(
        private val birthdayListDomain: BirthdayListDomain
    ) : BirthdayListDomainCompareMapper {
        override fun map(list: List<BirthdayDomain>): Boolean {
            val source = birthdayListDomain.asList()

            if (source.size != list.size) { return false }

            return source.zip(list).all {
                it.first.map(BirthdayCheckMapper.CompareObject(it.second))
            }
        }
    }

}