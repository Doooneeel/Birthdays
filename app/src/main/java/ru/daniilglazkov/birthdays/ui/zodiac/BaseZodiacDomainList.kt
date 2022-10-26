package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.zodiac.FetchZodiacDomainList
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain.*
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources

/**
 * @author Danil Glazkov on 25.10.2022, 04:51
 */
class BaseZodiacDomainList(private val resources: ManageResources) : FetchZodiacDomainList {

    override fun zodiacs(): List<ZodiacDomain> = listOf(
        Aries(resources.string(R.string.aries)),
        Taurus(resources.string(R.string.taurus)),
        Gemini(resources.string(R.string.gemini)),
        Cancer(resources.string(R.string.cancer)),
        Leo(resources.string(R.string.leo)),
        Virgo(resources.string(R.string.virgo)),
        Libra(resources.string(R.string.libra)),
        Scorpio(resources.string(R.string.scorpio)),
        Sagittarius(resources.string(R.string.sagittarius)),
        Capricorn(resources.string(R.string.capricorn)),
        Aquarius(resources.string(R.string.aquarius)),
        Pisces(resources.string(R.string.pisces)),
    )
}