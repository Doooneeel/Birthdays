package ru.daniilglazkov.birthdays.data.birthdays

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import ru.daniilglazkov.birthdays.data.database.converttype.LocalDateConvertType
import ru.daniilglazkov.birthdays.data.main.AbstractEntity
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 02:56
 */
@Entity
@TypeConverters(LocalDateConvertType::class)
class BirthdayEntity(
    @ColumnInfo(name = "birthday_name")
    val name: String,
    @ColumnInfo(name = "birthdate")
    val date: LocalDate,
) : AbstractEntity<BirthdayData>()  {
    override fun mapToData() = BirthdayData.Base(id, name, date)
}