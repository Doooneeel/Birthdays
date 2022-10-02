package ru.daniilglazkov.birthdays.data.showmode.converttype

import androidx.room.TypeConverter
import ru.daniilglazkov.birthdays.data.database.converttype.ConvertType
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 10.09.2022, 23:53
 */
class SortModeConvertType : ConvertType<SortMode> {

    @TypeConverter
    override fun toType(data: String): SortMode = SortMode.valueOf(data)

    @TypeConverter
    override fun fromType(data: SortMode): String = data.name
}