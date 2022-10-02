package ru.daniilglazkov.birthdays.data.showmode

import androidx.room.Dao
import androidx.room.Query
import ru.daniilglazkov.birthdays.data.database.BaseDataAccess

/**
 * @author Danil Glazkov on 06.09.2022, 18:20
 */
@Dao
interface ShowModeAccess : BaseDataAccess<ShowModeEntity> {

    @Query("SELECT * FROM show_mode")
    fun showMode(): ShowModeEntity?
}