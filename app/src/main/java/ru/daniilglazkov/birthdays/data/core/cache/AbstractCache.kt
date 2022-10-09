package ru.daniilglazkov.birthdays.data.core.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Danil Glazkov on 08.09.2022, 21:56
 */
@Entity
abstract class AbstractCache<T>(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
