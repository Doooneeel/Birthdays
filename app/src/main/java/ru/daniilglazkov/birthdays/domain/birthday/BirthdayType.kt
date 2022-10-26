package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.core.Matches

/**
 * @author Danil Glazkov on 04.09.2022, 02:32
 */
abstract class BirthdayType(private val key: String) : Matches<BirthdayType> {
    override fun matches(data: BirthdayType): Boolean = data.key == key

    object Base : BirthdayType(key = "BirthdayTypeBase")
    object Header : BirthdayType(key = "BirthdayTypeHeader")
}