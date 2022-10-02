package ru.daniilglazkov.birthdays.data.newbirthday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.database.converttype.LocalDateConvertType
import ru.daniilglazkov.birthdays.data.main.AbstractOneInstanceEntity
import java.time.LocalDate

/**
 * @author Danil Glazkov on 05.09.2022, 17:06
 */
@Entity(tableName = "new_birthday")
@TypeConverters(LocalDateConvertType::class)
class NewBirthdayEntity(
    @ColumnInfo(name = "new_birthday_name")
    var name: String,
    @ColumnInfo(name = "new_birthday_date")
    var date: LocalDate
) : AbstractOneInstanceEntity<NewBirthdayData>() {
    override fun mapToData() = NewBirthdayData.Base(name, date)
}