package ru.daniilglazkov.birthdays.data.settings.cache

import androidx.room.*

/**
 * @author Danil Glazkov on 06.09.2022, 00:48
 */
@Entity(tableName = "settings")
data class SettingsCache(
    val sortModeId: Int,
    val reverse: Boolean,
    val group: Boolean,
) {
    @PrimaryKey
    var id: Int = 0
}