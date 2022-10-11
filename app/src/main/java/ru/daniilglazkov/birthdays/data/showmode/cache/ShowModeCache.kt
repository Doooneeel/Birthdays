package ru.daniilglazkov.birthdays.data.showmode.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.core.cache.AbstractSingleInstanceCache
import ru.daniilglazkov.birthdays.data.showmode.cache.converttype.SortModeConvertType
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 06.09.2022, 00:48
 */
@Entity(tableName = "show_mode")
@TypeConverters(SortModeConvertType::class)
class ShowModeCache(
    @ColumnInfo(name = "sort_mode") val sort: SortMode,
    @ColumnInfo(name = "reverse") val reverse: Boolean,
    @ColumnInfo(name = "group") val group: Boolean,
) : AbstractSingleInstanceCache<ShowModeCache>()