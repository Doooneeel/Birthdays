package ru.daniilglazkov.birthdays.data.showmode.cache.converttype

import androidx.room.TypeConverter
import ru.daniilglazkov.birthdays.data.core.cache.converttype.ConvertType
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 10.09.2022, 23:53
 */
class SortModeConvertType : ConvertType<SortMode> {

    @TypeConverter
    override fun to(data: String): SortMode = SortMode.valueOf(data)

    @TypeConverter
    override fun from(data: SortMode): String = data.name
}