package ru.daniilglazkov.birthdays.data.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.daniilglazkov.birthdays.data.core.EntityToDataMapper

/**
 * @author Danil Glazkov on 08.09.2022, 04:34
 */
@Entity
abstract class AbstractOneInstanceEntity<T> : EntityToDataMapper<T> {
    @PrimaryKey
    var id: Int = 0
}
