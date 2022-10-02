package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.domain.range.RangeCategory
import ru.daniilglazkov.birthdays.domain.range.RangeGroup

/**
 * @author Danil Glazkov on 21.09.2022, 05:14
 */
class AgeGroupClassification : RangeGroup.AbstractInt() {
    override val ranges: List<RangeCategory<Int>> = listOf(
        AgeCategory.Child(),
        AgeCategory.Teenage(),
        AgeCategory.Young(),
        AgeCategory.Adult(),
        AgeCategory.Senior()
    )
}