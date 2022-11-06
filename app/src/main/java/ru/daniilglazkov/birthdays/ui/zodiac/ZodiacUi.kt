package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.core.Matches
import ru.daniilglazkov.birthdays.ui.core.view.AbstractResView

/**
 * @author Danil Glazkov on 23.10.2022, 18:04
 */
interface ZodiacUi : Matches<Int> {
    fun apply(name: AbstractResView.Text, image: AbstractResView.Image)

    abstract class Abstract(
        private val ordinal: Int,
        private val name: Int,
        private val image: Int = 0,
    ) : ZodiacUi {
        override fun matches(data: Int): Boolean = data == ordinal
        override fun apply(name: AbstractResView.Text, image: AbstractResView.Image) {
            name.map(this.name)
            image.map(this.image)
        }
    }

    object Aries       : Abstract(ordinal = 0, R.string.aries, R.drawable.ic_zodiac_aries_medium)
    object Taurus      : Abstract(ordinal = 1, R.string.taurus, R.drawable.ic_zodiac_taurus_medium)
    object Gemini      : Abstract(ordinal = 2, R.string.gemini, R.drawable.ic_zodiac_gemini_medium)
    object Cancer      : Abstract(ordinal = 3, R.string.cancer, R.drawable.ic_zodiac_cancer_medium)
    object Leo         : Abstract(ordinal = 4, R.string.leo, R.drawable.ic_zodiac_leo_medium)
    object Virgo       : Abstract(ordinal = 5, R.string.virgo, R.drawable.ic_zodiac_virgo_medium)
    object Libra       : Abstract(ordinal = 6, R.string.libra, R.drawable.ic_zodiac_libra_medium)
    object Scorpio     : Abstract(ordinal = 7, R.string.scorpio, R.drawable.ic_zodiac_scorpio_medium)
    object Sagittarius : Abstract(ordinal = 8, R.string.sagittarius, R.drawable.ic_zodiac_sagittarius_medium)
    object Capricorn   : Abstract(ordinal = 9, R.string.capricorn, R.drawable.ic_zodiac_capricorn_medium)
    object Aquarius    : Abstract(ordinal = 10, R.string.aquarius, R.drawable.ic_zodiac_aquarius_medium)
    object Pisces      : Abstract(ordinal = 11, R.string.pisces, R.drawable.ic_zodiac_pisces_medium)


    object Monkey : Abstract(ordinal = 0, R.string.monkey)
    object Rooster : Abstract(ordinal = 1, R.string.rooster)
    object Dog : Abstract(ordinal = 2, R.string.dog)
    object Pig : Abstract(ordinal = 3, R.string.pig)
    object Rat : Abstract(ordinal = 4, R.string.rat)
    object Ox : Abstract(ordinal = 5, R.string.ox)
    object Tiger : Abstract(ordinal = 6, R.string.tiger)
    object Rabbit : Abstract(ordinal = 7, R.string.rabbit)
    object Dragon : Abstract(ordinal = 8, R.string.dragon)
    object Snake : Abstract(ordinal = 9, R.string.snake)
    object Horse : Abstract(ordinal = 10, R.string.horse)
    object Goat : Abstract(ordinal = 11, R.string.goat)
}