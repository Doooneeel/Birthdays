package ru.daniilglazkov.birthdays.sl.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import ru.daniilglazkov.birthdays.data.core.cache.BirthdaysDatabase
import ru.daniilglazkov.birthdays.sl.core.ProvideDatabase
import ru.daniilglazkov.birthdays.ui.core.resources.ProvidePreferences

/**
 * @author Danil Glazkov on 16.02.2023, 17:50
 */
interface CacheModule : ProvideDatabase<BirthdaysDatabase>, ProvidePreferences {

    class Release(private val context: Context) : CacheModule {

        private val database by lazy {
            Room.databaseBuilder(context, BirthdaysDatabase::class.java, DATABASE_NAME)
                .build()
        }

        override fun provideDatabase(): BirthdaysDatabase = database

        override fun preferences(fileName: String): SharedPreferences =
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

        companion object {
            private const val DATABASE_NAME = "birthdays_database"
        }
    }

    class Debug(private val context: Context) : CacheModule {

        private val database by lazy {
            Room.inMemoryDatabaseBuilder(context, BirthdaysDatabase::class.java)
                .build()
        }

        override fun provideDatabase(): BirthdaysDatabase = database

        override fun preferences(fileName: String): SharedPreferences =
            context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

        companion object {
            private const val PREFERENCES_FILE_NAME = "birthdays_preferences_debug"
        }
    }
}