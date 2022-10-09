package ru.daniilglazkov.birthdays.data.core.cache

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * @author Danil Glazkov on 11.06.2022, 03:35
 */
interface ProvideDatabase<T : RoomDatabase> {
    fun provideDatabase(): T

    abstract class Abstract<T : RoomDatabase>(
        private val context: Context,
        private val name: String,
        private val type: Class<T>
    ) : ProvideDatabase<T> {
        protected abstract fun RoomDatabase.Builder<T>.configuration(): RoomDatabase.Builder<T>

        override fun provideDatabase(): T = Room.databaseBuilder(context, type, name)
            .configuration()
            .build()
    }
}
