package ru.daniilglazkov.birthdays.data.showmode.cache

import androidx.room.Dao
import androidx.room.Query
import ru.daniilglazkov.birthdays.data.core.cache.BaseDao

/**
 * @author Danil Glazkov on 06.09.2022, 18:20
 */
@Dao
interface ShowModeDao : BaseDao<ShowModeCache> {

    @Query("SELECT * FROM show_mode")
    fun showMode(): ShowModeCache?
}