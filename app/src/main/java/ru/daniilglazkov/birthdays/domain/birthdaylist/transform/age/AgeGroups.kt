package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age

import ru.daniilglazkov.birthdays.domain.range.RangeGroups
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeRange.*

/**
 * @author Danil Glazkov on 21.09.2022, 05:14
 */
interface AgeGroups : RangeGroups<AgeRange, Int> {

    class Base(
        ageRanges: List<AgeRange> = AGE_RANGES
    ) : RangeGroups.Abstract<AgeRange, Int>(ageRanges), AgeGroups {
        companion object {
            private val AGE_RANGES = listOf(Child, Teenage, Young, Adult, Senior)
        }
    }
}