package ru.daniilglazkov.birthdays.domain.birthday

import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.10.2022, 16:17
 */
interface BirthdayCheckMapper : BirthdayDomain.Mapper<Boolean> {

    object IsHeader : BirthdayCheckMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            type.matches(BirthdayType.Header)
    }

    object IsNotHeader : BirthdayCheckMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
           !IsHeader.map(id, name, date, type)
    }

    class Compare(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
        private val type: BirthdayType
    ) : BirthdayDomain.Mapper<Boolean> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Boolean =
            this.id == id && this.name == name && this.date == date && this.type.matches(type)
    }

    class CompareObject(private val birthday: BirthdayDomain) : BirthdayCheckMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            birthday.map(Compare(id, name, date, type))
    }
}