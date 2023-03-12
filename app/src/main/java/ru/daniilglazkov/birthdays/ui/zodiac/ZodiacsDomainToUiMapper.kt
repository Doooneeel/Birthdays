package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacsDomain

/**
 * @author Danil Glazkov on 02.11.2022, 11:10
 */
interface ZodiacsDomainToUiMapper : ZodiacsDomain.Mapper<ZodiacsUi> {

    class Base(
        private val greekZodiacDomainToUiMapper: ZodiacDomainToUiMapper,
        private val zodiacDomainToUiMapper: ZodiacDomainToUiMapper,
    ) : ZodiacsDomainToUiMapper {
        override fun map(greek: ZodiacDomain, chinese: ZodiacDomain) = ZodiacsUi.Base(
            greek.map(greekZodiacDomainToUiMapper),
            chinese.map(zodiacDomainToUiMapper)
        )
    }
}