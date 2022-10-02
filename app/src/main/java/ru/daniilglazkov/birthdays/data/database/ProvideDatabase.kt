package ru.daniilglazkov.birthdays.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.daniilglazkov.birthdays.data.main.BirthdaysDatabase

/**
 * @author Danil Glazkov on 11.06.2022, 03:35
 */
interface ProvideDatabase<T : RoomDatabase> {
    fun provideDatabase(): T

    abstract class Abstract<T : RoomDatabase>(
        context: Context,
        name: String,
        clazz: Class<T>
    ) : ProvideDatabase<T> {
        protected val builder: RoomDatabase.Builder<T> by lazy {
            Room.databaseBuilder(context, clazz, name)
        }
    }

    class Birthdays(
        context: Context,
        name: String,
    ) : Abstract<BirthdaysDatabase>(
        context,
        name,
        BirthdaysDatabase::class.java
    ) {
        override fun provideDatabase() = builder
            .allowMainThreadQueries()
            .build()
    }
}
