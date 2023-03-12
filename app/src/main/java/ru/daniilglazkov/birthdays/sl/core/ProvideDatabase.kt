package ru.daniilglazkov.birthdays.sl.core

import androidx.room.RoomDatabase

/**
 * @author Danil Glazkov on 27.02.2023, 8:47
 */
interface ProvideDatabase<T : RoomDatabase> {
    fun provideDatabase(): T
}