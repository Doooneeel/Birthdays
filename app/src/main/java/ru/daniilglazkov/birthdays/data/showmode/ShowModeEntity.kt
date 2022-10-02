package ru.daniilglazkov.birthdays.data.showmode

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.main.AbstractOneInstanceEntity
import ru.daniilglazkov.birthdays.data.showmode.converttype.SortModeConvertType
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 06.09.2022, 00:48
 */
@Entity(tableName = "show_mode")
@TypeConverters(SortModeConvertType::class)
class ShowModeEntity(
    @ColumnInfo(name = "show_mode_sort")
    val sort: SortMode,
    @ColumnInfo(name = "show_mode_reverse")
    val reverse: Boolean,
    @ColumnInfo(name = "show_mode_group")
    val group: Boolean,
) : AbstractOneInstanceEntity<ShowModeData>() {
    override fun mapToData() = ShowModeData.Base(sort, reverse, group)
}