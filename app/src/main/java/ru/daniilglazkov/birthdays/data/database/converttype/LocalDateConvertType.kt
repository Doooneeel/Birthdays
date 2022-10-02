package ru.daniilglazkov.birthdays.data.database.converttype

import androidx.room.TypeConverter
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.09.2022, 00:10
 */
class LocalDateConvertType : ConvertType<LocalDate> {

    @TypeConverter
    override fun toType(data: String): LocalDate {
        val date = data.split(SEPARATOR).map { it.toInt() }
        return LocalDate.of(date[0], date[1], date[2])
    }

    @TypeConverter
    override fun fromType(data: LocalDate): String =
        "${ data.year }$SEPARATOR${ data.monthValue }$SEPARATOR${ data.dayOfMonth }"

    companion object {
        private const val SEPARATOR: Char = '-'
    }
}