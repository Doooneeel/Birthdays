package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.core.Matches
import ru.daniilglazkov.birthdays.ui.core.view.AbstractViewTextWithIcon

/**
 * @author Danil Glazkov on 23.10.2022, 18:04
 */
interface ZodiacUi : Matches<Int> {
    fun apply(view: AbstractViewTextWithIcon)


    abstract class Abstract(
        private val ordinal: Int,
        private val name: Int,
        private val image: Int,
    ) : ZodiacUi {
        override fun matches(data: Int): Boolean = data == ordinal
        override fun apply(view: AbstractViewTextWithIcon) =
            view.map(name, image)
    }

    class Aries : Abstract(ordinal = 0, R.string.aries, R.drawable.ic_zodiac_aries)
    class Taurus : Abstract(ordinal = 1, R.string.taurus, R.drawable.ic_zodiac_taurus)
    class Gemini : Abstract(ordinal = 2, R.string.gemini, R.drawable.ic_zodiac_gemini)
    class Cancer : Abstract(ordinal = 3, R.string.cancer, R.drawable.ic_zodiac_cancer)
    class Leo : Abstract(ordinal = 4, R.string.leo, R.drawable.ic_zodiac_leo)
    class Virgo : Abstract(ordinal = 5, R.string.virgo, R.drawable.ic_zodiac_virgo)
    class Libra : Abstract(ordinal = 6, R.string.libra, R.drawable.ic_zodiac_libra)
    class Scorpio: Abstract(ordinal = 7, R.string.scorpio, R.drawable.ic_zodiac_scorpio)
    class Sagittarius : Abstract(ordinal = 8, R.string.sagittarius, R.drawable.ic_zodiac_sagittarius)
    class Capricorn : Abstract(ordinal = 9, R.string.capricorn, R.drawable.ic_zodiac_capricorn)
    class Aquarius : Abstract(ordinal = 10, R.string.aquarius, R.drawable.ic_zodiac_aquarius)
    class Pisces : Abstract(ordinal = 11, R.string.pisces, R.drawable.ic_zodiac_pisces)
}