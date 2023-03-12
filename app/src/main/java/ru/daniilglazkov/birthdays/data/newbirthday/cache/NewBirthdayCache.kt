package ru.daniilglazkov.birthdays.data.newbirthday.cache

import androidx.room.*

/**
 * @author Danil Glazkov on 05.09.2022, 17:06
 */
@Entity(tableName = "new_birthday")
data class NewBirthdayCache(
    var name: String,
    var epochDay: Long
) {
    @PrimaryKey
    var id: Int = 0
}