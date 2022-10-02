package ru.daniilglazkov.birthdays.data.newbirthday

import androidx.room.Dao
import androidx.room.Query
import ru.daniilglazkov.birthdays.data.database.BaseDataAccess

/**
 * @author Danil Glazkov on 06.09.2022, 18:33
 */
@Dao
interface NewBirthdayAccess : BaseDataAccess<NewBirthdayEntity> {

    @Query("SELECT * FROM new_birthday")
    fun newBirthday(): NewBirthdayEntity?
}