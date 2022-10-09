package ru.daniilglazkov.birthdays.data.core.cache.converttype

import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.09.2022, 00:10
 */
class LocalDateConvertType : ConvertType<LocalDate> {

    @TypeConverter
    override fun to(data: String): LocalDate {
        val date = data.split(SEPARATOR).map { it.toInt() }
        return LocalDate.of(date[0], date[1], date[2])
    }

    @TypeConverter
    override fun from(data: LocalDate): String {
        return "${ data.year }$SEPARATOR${ data.monthValue }$SEPARATOR${ data.dayOfMonth }"
    }

    companion object {
        private const val SEPARATOR: Char = '-'
    }
}