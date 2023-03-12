package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age

import ru.daniilglazkov.birthdays.domain.range.Range

/**
 * @author Danil Glazkov on 21.09.2022, 05:12
 */
abstract class AgeRange(range: IntRange) : Range.Integer(range) {

    data class Base(private val range: IntRange) : AgeRange(range)


    object Child : AgeRange(range = Int.MIN_VALUE..12)
    object Teenage : AgeRange(range = 13..18)
    object Young : AgeRange(range = 19..35)
    object Adult : AgeRange(range = 36..59)
    object Senior : AgeRange(range = 60..Int.MAX_VALUE)
}
