package ru.daniilglazkov.birthdays.data.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.daniilglazkov.birthdays.data.core.EntityToDataMapper

/**
 * @author Danil Glazkov on 08.09.2022, 21:56
 */
@Entity
abstract class AbstractEntity<T>(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
) : EntityToDataMapper<T>
