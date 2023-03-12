package ru.daniilglazkov.birthdays.data.birthdaylist.cache

import ru.daniilglazkov.birthdays.data.birthdaylist.BirthdayData

/**
 * @author Danil Glazkov on 09.10.2022, 19:56
 */
interface BirthdayDataToCacheMapper : BirthdayData.Mapper<BirthdayCache> {

    class Base : BirthdayDataToCacheMapper {
        override fun map(id: Int, name: String, epochDay: Long) = BirthdayCache(name, epochDay)
    }
}