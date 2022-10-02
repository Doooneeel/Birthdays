package ru.daniilglazkov.birthdays.data.main

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.birthdays.BirthdayEntity
import ru.daniilglazkov.birthdays.data.birthdays.BirthdaysAccess
import ru.daniilglazkov.birthdays.data.database.converttype.LocalDateConvertType
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayAccess
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayEntity
import ru.daniilglazkov.birthdays.data.showmode.ShowModeAccess
import ru.daniilglazkov.birthdays.data.showmode.ShowModeEntity
import ru.daniilglazkov.birthdays.data.showmode.converttype.SortModeConvertType

/**
 * @author Danil Glazkov on 06.09.2022, 17:55
 */
@TypeConverters(LocalDateConvertType::class, SortModeConvertType::class)
@Database(
    entities = [BirthdayEntity::class, NewBirthdayEntity::class, ShowModeEntity::class],
    exportSchema = false,
    version = 1
)
abstract class BirthdaysDatabase : RoomDatabase(), ProvideBirthdaysAccess, ProvideNewBirthdayAccess,
    ProvideShowModeAccess

interface ProvideBirthdaysAccess {
    fun birthdaysAccess(): BirthdaysAccess
}
interface ProvideNewBirthdayAccess {
    fun newBirthdayAccess(): NewBirthdayAccess
}
interface ProvideShowModeAccess {
    fun showModeAccess(): ShowModeAccess
}

