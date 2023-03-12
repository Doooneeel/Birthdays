package ru.daniilglazkov.birthdays.data.birthdaylist.cache

import androidx.room.*

/**
 * @author Danil Glazkov on 11.06.2022, 02:56
 */
@Entity(tableName = "birthdays")
data class BirthdayCache(
    val name: String,
    val epochDay: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}