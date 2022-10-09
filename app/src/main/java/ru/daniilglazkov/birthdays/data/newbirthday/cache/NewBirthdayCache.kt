package ru.daniilglazkov.birthdays.data.newbirthday.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.core.cache.converttype.LocalDateConvertType
import ru.daniilglazkov.birthdays.data.core.cache.AbstractSingleInstanceCache
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData
import java.time.LocalDate

/**
 * @author Danil Glazkov on 05.09.2022, 17:06
 */
@Entity(tableName = "new_birthday")
@TypeConverters(LocalDateConvertType::class)
class NewBirthdayCache(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "date")
    var date: LocalDate
) : AbstractSingleInstanceCache<NewBirthdayData>()