package ru.daniilglazkov.birthdays.domain.birthday

import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.10.2022, 16:17
 */
interface BirthdayCheckMapper : BirthdayDomain.Mapper<Boolean> {

    class IsHeader : BirthdayCheckMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            type.matches(BirthdayType.Header)
    }

    class IsNotHeader : BirthdayCheckMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            type.matches(BirthdayType.Header).not()
    }

    class Compare(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
        private val type: BirthdayType
    ) : BirthdayCheckMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            this.id == id && this.name == name && this.date == date && this.type.matches(type)
    }
}