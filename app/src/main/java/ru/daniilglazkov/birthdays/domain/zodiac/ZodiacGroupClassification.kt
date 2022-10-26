package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.range.RangeGroup

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
interface ZodiacGroupClassification : RangeGroup<ZodiacDomain, Int> {

    class Base(
        zodiacList: FetchZodiacDomainList
    ) : RangeGroup.Abstract<ZodiacDomain, Int>(), ZodiacGroupClassification {
        override val ranges = zodiacList.zodiacs()
    }
}