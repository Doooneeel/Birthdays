package ru.daniilglazkov.birthdays.data.core.cache

import androidx.room.*
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdayCache
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.ProvideBirthdaysDao
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCache
import ru.daniilglazkov.birthdays.data.newbirthday.cache.ProvideNewBirthdayDao
import ru.daniilglazkov.birthdays.data.settings.cache.ProvideSettingsDao
import ru.daniilglazkov.birthdays.data.settings.cache.SettingsCache

/**
 * @author Danil Glazkov on 06.09.2022, 17:55
 */
@Database(
    entities = [BirthdayCache::class, NewBirthdayCache::class, SettingsCache::class],
    exportSchema = false,
    version = 1
)
abstract class BirthdaysDatabase : RoomDatabase(),
    ProvideBirthdaysDao,
    ProvideNewBirthdayDao,
    ProvideSettingsDao