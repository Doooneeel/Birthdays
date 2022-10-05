package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 21.09.2022, 05:12
 */
sealed class AgeRangeCategory(range: IntRange) : RangeCategory.AbstractInt(range) {

    class Child : AgeRangeCategory(range = 0..12)
    class Teenage : AgeRangeCategory(range = 13..18)
    class Young : AgeRangeCategory(range = 19..35)
    class Adult : AgeRangeCategory(range = 35..59)
    class Senior : AgeRangeCategory(range = 60..Int.MAX_VALUE)
}
