package ru.daniilglazkov.birthdays.data.settings.cache

import androidx.room.*
import ru.daniilglazkov.birthdays.data.core.cache.BaseDao

/**
 * @author Danil Glazkov on 06.09.2022, 18:20
 */
@Dao
interface SettingsDao : BaseDao<SettingsCache> {

    @Query("SELECT * FROM settings")
    fun settings(): SettingsCache?
}