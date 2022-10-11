package ru.daniilglazkov.birthdays.domain.showmode.age

import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 21.09.2022, 05:12
 */
abstract class AgeRangeCategory(range: IntRange) : RangeCategory.AbstractInt(range) {

    object Child : AgeRangeCategory(range = 0..12)
    object Teenage : AgeRangeCategory(range = 13..18)
    object Young : AgeRangeCategory(range = 19..35)
    object Adult : AgeRangeCategory(range = 35..59)
    object Senior : AgeRangeCategory(range = 60..Int.MAX_VALUE)
}
