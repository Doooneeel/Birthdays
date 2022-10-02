package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 21.09.2022, 05:12
 */
sealed class AgeCategory(range: IntRange) : RangeCategory.AbstractInt(range) {

    class Child : AgeCategory(range = 0..12)
    class Teenage : AgeCategory(range = 13..18)
    class Young : AgeCategory(range = 19..35)
    class Adult : AgeCategory(range = 35..59)
    class Senior : AgeCategory(range = 60..Int.MAX_VALUE)
}
