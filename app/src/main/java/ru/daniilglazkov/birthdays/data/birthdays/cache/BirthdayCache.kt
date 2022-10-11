package ru.daniilglazkov.birthdays.data.birthdays.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayData
import ru.daniilglazkov.birthdays.data.core.cache.AbstractCache
import ru.daniilglazkov.birthdays.data.core.cache.converttype.LocalDateConvertType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 02:56
 */
@Entity(tableName = "birthdays")
@TypeConverters(LocalDateConvertType::class)
class BirthdayCache(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val date: LocalDate,
) : AbstractCache<BirthdayData>()