package ru.daniilglazkov.birthdays.domain.zodiac

/**
 * @author Danil Glazkov on 02.11.2022, 09:36
 */
interface FetchChineseZodiacDomainList {
    fun chineseZodiacs(): List<ChineseZodiacDomain>
}