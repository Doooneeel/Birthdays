package ru.daniilglazkov.birthdays.data.birthdays.cache

import ru.daniilglazkov.birthdays.data.birthdays.BirthdayData
import java.time.LocalDate

/**
 * @author Danil Glazkov on 09.10.2022, 19:56
 */
interface BirthdayDataToCacheMapper : BirthdayData.Mapper<BirthdayCache> {

    class Base : BirthdayDataToCacheMapper {
        override fun map(id: Int, name: String, date: LocalDate): BirthdayCache =
            BirthdayCache(name, date)
    }
}