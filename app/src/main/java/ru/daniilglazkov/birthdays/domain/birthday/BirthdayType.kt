package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.core.Matches

/**
 * @author Danil Glazkov on 04.09.2022, 02:32
 */
abstract class BirthdayType(private val id: String) : Matches<BirthdayType> {
    override fun matches(data: BirthdayType): Boolean = data.id == id

    object Base : BirthdayType("BirthdayTypeBase")
    object Today : BirthdayType("BirthdayTypeToday")
    object Header : BirthdayType("BirthdayTypeHeader")
}