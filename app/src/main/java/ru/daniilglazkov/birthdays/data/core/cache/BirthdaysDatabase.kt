package ru.daniilglazkov.birthdays.data.core.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.birthdays.cache.BirthdayCache
import ru.daniilglazkov.birthdays.data.birthdays.cache.ProvideBirthdaysDao
import ru.daniilglazkov.birthdays.data.core.cache.converttype.LocalDateConvertType
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCache
import ru.daniilglazkov.birthdays.data.newbirthday.cache.ProvideNewBirthdayDao
import ru.daniilglazkov.birthdays.data.showmode.cache.ProvideShowModeDao
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeCache
import ru.daniilglazkov.birthdays.data.showmode.cache.converttype.SortModeConvertType

/**
 * @author Danil Glazkov on 06.09.2022, 17:55
 */
@TypeConverters(LocalDateConvertType::class, SortModeConvertType::class)
@Database(
    entities = [BirthdayCache::class, NewBirthdayCache::class, ShowModeCache::class],
    exportSchema = false,
    version = 1
)
abstract class BirthdaysDatabase : RoomDatabase(),
    ProvideBirthdaysDao,
    ProvideNewBirthdayDao,
    ProvideShowModeDao