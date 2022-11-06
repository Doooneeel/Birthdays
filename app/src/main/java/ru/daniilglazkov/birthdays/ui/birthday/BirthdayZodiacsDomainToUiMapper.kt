package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayZodiacsDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.ui.zodiac.ChineseZodiacDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacDomainToUiMapper

/**
 * @author Danil Glazkov on 02.11.2022, 11:10
 */
interface BirthdayZodiacsDomainToUiMapper : BirthdayZodiacsDomain.Mapper<BirthdayZodiacsUi> {

    class Base(
        private val zodiacDomainToUiMapper: ZodiacDomainToUiMapper,
        private val chineseZodiacDomainToUiMapper: ChineseZodiacDomainToUiMapper,
    ) : BirthdayZodiacsDomainToUiMapper {
        override fun map(base: ZodiacDomain, chinese: ChineseZodiacDomain) = BirthdayZodiacsUi.Base(
            base.map(zodiacDomainToUiMapper),
            chinese.map(chineseZodiacDomainToUiMapper)
        )
    }
}