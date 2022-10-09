package ru.daniilglazkov.birthdays.data.newbirthday.cache

import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData
import java.time.LocalDate

/**
 * @author Danil Glazkov on 09.10.2022, 19:47
 */
interface NewBirthdayDataToCacheMapper : NewBirthdayData.Mapper<NewBirthdayCache> {

    class Base : NewBirthdayDataToCacheMapper {
        override fun map(name: String, date: LocalDate): NewBirthdayCache =
            NewBirthdayCache(name, date)
    }
}