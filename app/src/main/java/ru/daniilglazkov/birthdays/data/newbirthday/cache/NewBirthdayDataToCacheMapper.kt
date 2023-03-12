package ru.daniilglazkov.birthdays.data.newbirthday.cache

import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData

/**
 * @author Danil Glazkov on 09.10.2022, 19:47
 */
interface NewBirthdayDataToCacheMapper : NewBirthdayData.Mapper<NewBirthdayCache> {

    class Base : NewBirthdayDataToCacheMapper {
        override fun map(name: String, epochDay: Long) = NewBirthdayCache(name, epochDay)
    }
}