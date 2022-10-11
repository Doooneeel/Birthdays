package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType.Zodiac
import ru.daniilglazkov.birthdays.domain.core.TextFormat
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources

/**
 * @author Danil Glazkov on 11.10.2022, 21:48
 */
interface ZodiacTextFormat : TextFormat<Zodiac> {

    class Base(private val manageResources: ManageResources) : ZodiacTextFormat {
        override fun format(source: Zodiac) = when(source) {
            is Zodiac.Aries -> manageResources.string(R.string.aries)
            is Zodiac.Taurus -> manageResources.string(R.string.taurus)
            is Zodiac.Gemini -> manageResources.string(R.string.gemini)
            is Zodiac.Cancer -> manageResources.string(R.string.cancer)
            is Zodiac.Leo -> manageResources.string(R.string.leo)
            is Zodiac.Virgo -> manageResources.string(R.string.virgo)
            is Zodiac.Libra -> manageResources.string(R.string.libra)
            is Zodiac.Scorpio -> manageResources.string(R.string.scorpio)
            is Zodiac.Sagittarius -> manageResources.string(R.string.sagittarius)
            is Zodiac.Capricorn -> manageResources.string(R.string.capricorn)
            is Zodiac.Aquarius -> manageResources.string(R.string.aquarius)
            is Zodiac.Pisces -> manageResources.string(R.string.pisces)
        }
    }
}