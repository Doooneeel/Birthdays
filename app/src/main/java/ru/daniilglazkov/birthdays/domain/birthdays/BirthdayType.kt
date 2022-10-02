package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.Matches

/**
 * @author Danil Glazkov on 04.09.2022, 02:32
 */
abstract class BirthdayType(private val id: String) : Matches<BirthdayType> {
    override fun matches(data: BirthdayType): Boolean = data.id == id

    object Base : BirthdayType("BirthdayTypeBase")

    object Header : BirthdayType("BirthdayTypeHeader")

    object Error : BirthdayType("BirthdayTypeEmpty")

    object Today : BirthdayType("BirthdayTypeToday")
}
