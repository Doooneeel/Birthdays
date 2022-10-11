package ru.daniilglazkov.birthdays.domain.showmode.age

import ru.daniilglazkov.birthdays.domain.showmode.age.AgeRangeCategory.*
import ru.daniilglazkov.birthdays.domain.range.RangeGroup

/**
 * @author Danil Glazkov on 21.09.2022, 05:14
 */
interface AgeGroupClassification : RangeGroup<AgeRangeCategory, Int> {

    class Base : RangeGroup.Abstract<AgeRangeCategory, Int>(), AgeGroupClassification {
        override val ranges: List<AgeRangeCategory> = listOf(Child, Teenage, Young, Adult, Senior)
    }
}