package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomainList
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 25.10.2022, 04:51
 */
class BaseGreekZodiacDomainList(resources: ProvideString) : GreekZodiacDomainList {

    override fun greekZodiacs(): List<GreekZodiacDomain> = zodiacs

    private val zodiacs = listOf(
        GreekZodiacDomain.Aries(resources.string(R.string.aries)),
        GreekZodiacDomain.Taurus(resources.string(R.string.taurus)),
        GreekZodiacDomain.Gemini(resources.string(R.string.gemini)),
        GreekZodiacDomain.Cancer(resources.string(R.string.cancer)),
        GreekZodiacDomain.Leo(resources.string(R.string.leo)),
        GreekZodiacDomain.Virgo(resources.string(R.string.virgo)),
        GreekZodiacDomain.Libra(resources.string(R.string.libra)),
        GreekZodiacDomain.Scorpio(resources.string(R.string.scorpio)),
        GreekZodiacDomain.Sagittarius(resources.string(R.string.sagittarius)),
        GreekZodiacDomain.Capricorn(resources.string(R.string.capricorn)),
        GreekZodiacDomain.Aquarius(resources.string(R.string.aquarius)),
        GreekZodiacDomain.Pisces(resources.string(R.string.pisces)),
    )
}