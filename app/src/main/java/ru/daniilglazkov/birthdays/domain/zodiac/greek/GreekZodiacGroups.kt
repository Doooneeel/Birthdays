package ru.daniilglazkov.birthdays.domain.zodiac.greek

import ru.daniilglazkov.birthdays.domain.range.RangeGroups

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
interface GreekZodiacGroups : RangeGroups<GreekZodiacDomain, Int> {

    class Base(
        zodiacList: GreekZodiacDomainList
    ) : RangeGroups.Abstract<GreekZodiacDomain, Int>(
        zodiacList.greekZodiacs()
    ) , GreekZodiacGroups
}