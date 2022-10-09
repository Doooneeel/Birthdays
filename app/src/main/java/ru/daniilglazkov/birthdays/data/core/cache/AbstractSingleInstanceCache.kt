package ru.daniilglazkov.birthdays.data.core.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Danil Glazkov on 08.09.2022, 04:34
 */
@Entity
abstract class AbstractSingleInstanceCache<T> {
    @PrimaryKey
    var id: Int = 0
}