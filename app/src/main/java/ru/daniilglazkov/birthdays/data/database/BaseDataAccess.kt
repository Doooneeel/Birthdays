package ru.daniilglazkov.birthdays.data.database

import androidx.room.*


/**
 * @author Danil Glazkov on 08.09.2022, 19:32
 */
@Dao
interface BaseDataAccess<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: T)

    @Update
    fun update(data: T)

    @Delete
    fun delete(data: T)
}