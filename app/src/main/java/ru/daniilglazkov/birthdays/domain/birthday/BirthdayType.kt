package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.core.Matches

/**
 * @author Danil Glazkov on 04.09.2022, 02:32
 */
abstract class BirthdayType(private val id: String) : Matches<BirthdayType> {
    override fun matches(data: BirthdayType): Boolean = data.id == id

    object Base : BirthdayType(id = "BirthdayTypeBase")
    object Today : BirthdayType(id = "BirthdayTypeToday")
    object Header : BirthdayType(id = "BirthdayTypeHeader")

    sealed class Zodiac(id: String) : BirthdayType(id) {
        object Aries       : Zodiac(id = "zodiac_aries")
        object Taurus      : Zodiac(id = "zodiac_taurus")
        object Gemini      : Zodiac(id = "zodiac_gemini")
        object Cancer      : Zodiac(id = "zodiac_cancer")
        object Leo         : Zodiac(id = "zodiac_leo")
        object Virgo       : Zodiac(id = "zodiac_virgo")
        object Libra       : Zodiac(id = "zodiac_libra")
        object Scorpio     : Zodiac(id = "zodiac_scorpio")
        object Sagittarius : Zodiac(id = "zodiac_sagittarius")
        object Capricorn   : Zodiac(id = "zodiac_capricorn")
        object Aquarius    : Zodiac(id = "zodiac_aquarius")
        object Pisces      : Zodiac(id = "zodiac_pisces")
    }
}