package ru.daniilglazkov.birthdays.data.birthdays

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 00:56
 */
interface BirthdayData {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(
        private val id: Int,
        private val name: String,
        private val date: LocalDate
    ) : BirthdayData {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, date)
    }

    class Fail : Abstract(FAIL_ID, "", LocalDate.MIN)
    class Base(id: Int, name: String, date: LocalDate) : Abstract(id, name, date)

    interface Mapper<T> {
        fun map(id: Int, name: String, date: LocalDate): T

        class ToDomain : Mapper<BirthdayDomain> {
            override fun map(id: Int, name: String, date: LocalDate): BirthdayDomain {
                return if (id == FAIL_ID) {
                    BirthdayDomain.Fail()
                } else {
                    BirthdayDomain.Base(id, name, date)
                }
            }
        }
        class ToDatabaseModel : Mapper<BirthdayEntity> {
            override fun map(id: Int, name: String, date: LocalDate) = BirthdayEntity(name, date)
        }
    }
    companion object {
        private const val FAIL_ID = -1
    }
}