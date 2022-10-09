package ru.daniilglazkov.birthdays.data.newbirthday.cache

import androidx.room.Dao
import androidx.room.Query
import ru.daniilglazkov.birthdays.data.core.cache.BaseDao

/**
 * @author Danil Glazkov on 06.09.2022, 18:33
 */
@Dao
interface NewBirthdayDao : BaseDao<NewBirthdayCache> {

    @Query("SELECT * FROM new_birthday")
    fun newBirthday(): NewBirthdayCache?
}