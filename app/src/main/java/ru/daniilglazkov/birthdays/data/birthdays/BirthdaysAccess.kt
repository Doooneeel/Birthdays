package ru.daniilglazkov.birthdays.data.birthdays

import androidx.room.Dao
import androidx.room.Query
import ru.daniilglazkov.birthdays.data.database.BaseDataAccess

/**
 * @author Danil Glazkov on 06.09.2022, 17:40
 */
@Dao
interface BirthdaysAccess : BaseDataAccess<BirthdayEntity> {

    @Query("SELECT * FROM BirthdayEntity")
    fun allBirthdays(): List<BirthdayEntity>

    @Query("SELECT * FROM BirthdayEntity WHERE id = :id")
    fun find(id: Int): BirthdayEntity?

    @Query("DELETE FROM BirthdayEntity WHERE id = :id")
    fun delete(id: Int)
}