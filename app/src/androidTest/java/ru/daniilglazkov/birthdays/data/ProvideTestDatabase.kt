package ru.daniilglazkov.birthdays.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import ru.daniilglazkov.birthdays.sl.core.ProvideDatabase

/**
 * @author Danil Glazkov on 27.02.2023, 8:46
 */
class ProvideTestDatabase<T : RoomDatabase>(private val clazz: Class<T>) : ProvideDatabase<T> {
    override fun provideDatabase(): T =
        Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), clazz)
            .allowMainThreadQueries()
            .build()
}