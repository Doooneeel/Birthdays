package ru.daniilglazkov.birthdays.data.birthdays.cache

import androidx.room.Dao
import androidx.room.Query
import ru.daniilglazkov.birthdays.data.core.cache.BaseDao

/**
 * @author Danil Glazkov on 06.09.2022, 17:40
 */
@Dao
interface BirthdaysDao : BaseDao<BirthdayCache> {

    @Query("SELECT * FROM birthdays")
    fun allBirthdays(): List<BirthdayCache>

    @Query("SELECT * FROM birthdays WHERE id = :id")
    fun find(id: Int): BirthdayCache?

    @Query("DELETE FROM birthdays WHERE id = :id")
    fun delete(id: Int)
}