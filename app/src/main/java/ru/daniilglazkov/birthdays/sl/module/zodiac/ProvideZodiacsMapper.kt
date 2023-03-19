package ru.daniilglazkov.birthdays.sl.module.zodiac

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayZodiacMapper

/**
 * @author Danil Glazkov on 19.03.2023, 4:31
 */
interface ProvideZodiacsMapper {

    fun provideChineseMapper(): BirthdayZodiacMapper

    fun provideGreekMapper(): BirthdayZodiacMapper

}