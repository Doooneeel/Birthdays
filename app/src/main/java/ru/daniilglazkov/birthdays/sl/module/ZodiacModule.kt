package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayZodiacMapper
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import ru.daniilglazkov.birthdays.ui.zodiac.BaseChineseZodiacDomainList
import ru.daniilglazkov.birthdays.ui.zodiac.BaseGreekZodiacDomainList

/**
 * @author Danil Glazkov on 16.02.2023, 18:18
 */
interface ZodiacModule {

    fun provideGreekMapper(): BirthdayZodiacMapper

    fun provideGreekZodiacGroup(): GreekZodiacGroups

    fun provideChineseMapper(): BirthdayZodiacMapper


    class Base(resources: ProvideString) : ZodiacModule {

        private val greekZodiacs = GreekZodiacGroups.Base(
            BaseGreekZodiacDomainList(resources)
        )

        private val chinese = BirthdayZodiacMapper.Chinese(
            BaseChineseZodiacDomainList(resources)
        )

        private val greek = BirthdayZodiacMapper.Greek(greekZodiacs)


        override fun provideGreekZodiacGroup() = greekZodiacs

        override fun provideGreekMapper(): BirthdayZodiacMapper = greek

        override fun provideChineseMapper(): BirthdayZodiacMapper = chinese
    }

}