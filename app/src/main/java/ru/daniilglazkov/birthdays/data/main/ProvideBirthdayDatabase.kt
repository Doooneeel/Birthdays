package ru.daniilglazkov.birthdays.data.main

import android.content.Context
import androidx.room.RoomDatabase
import ru.daniilglazkov.birthdays.data.core.cache.BirthdaysDatabase
import ru.daniilglazkov.birthdays.data.core.cache.ProvideDatabase

/**
 * @author Danil Glazkov on 09.10.2022, 20:26
 */
abstract class ProvideBirthdayDatabase(
    context: Context,
    name: String,
) : ProvideDatabase.Abstract<BirthdaysDatabase>(
    BirthdaysDatabase::class.java,
    context,
    name
) {
    class Release(context: Context, name: String) : ProvideBirthdayDatabase(context, name) {
        override fun RoomDatabase.Builder<BirthdaysDatabase>.configuration() =
            allowMainThreadQueries()
    }

    class Debug(context: Context) : ProvideBirthdayDatabase(context, DATABASE_NAME) {
        override fun RoomDatabase.Builder<BirthdaysDatabase>.configuration() =
            allowMainThreadQueries()

        companion object {
            private const val DATABASE_NAME = "debug-birthday-database"
        }
    }
}