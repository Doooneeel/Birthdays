package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.Matches
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 01:00
 */
interface BirthdayDomain : Matches<BirthdayDomain> {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
    ) : BirthdayDomain {
        protected abstract val type: BirthdayType

        private val compare by lazy { CheckMapper.Compare(id, name, date, type) }

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, date, type)
        override fun matches(data: BirthdayDomain): Boolean = data.map(compare)
    }

    class Base(id: Int, name: String, date: LocalDate) : Abstract(id, name, date) {
        private val now = LocalDate.now()

        override val type = if (date.withYear(now.year) == now) BirthdayType.Today
            else BirthdayType.Base
    }
    class Fail : Abstract(-1, "", LocalDate.MIN) {
        override val type = BirthdayType.Error
    }

    interface Mapper<T> {
        fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T

        abstract class Abstract<T>(private val value: (Int, String, LocalDate) -> T) : Mapper<T> {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T {
                return value.invoke(id, name, date)
            }
        }
    }
    interface CheckMapper : Mapper<Boolean> {

        class IsHeader : CheckMapper {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
                type.matches(BirthdayType.Header)
        }
        class IsNotHeader : CheckMapper {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
                type.matches(BirthdayType.Header).not()
        }
        class Compare(
            private val id: Int,
            private val name: String,
            private val date: LocalDate,
            private val type: BirthdayType
        ) : CheckMapper {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
                (this.id == id)
                    .and(this.name == name)
                    .and(this.date == date)
                    .and(this.type.matches(type))
        }
    }

}