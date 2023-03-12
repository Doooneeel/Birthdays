package ru.daniilglazkov.birthdays.domain.birthdaylist

/**
 * @author Danil Glazkov on 19.02.2023, 00:53
 */
interface BirthdayListResponse {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {

        fun map(birthdays: BirthdayListDomain): T

        fun map(exception: Exception): T

    }


    data class Success(private val birthdays: BirthdayListDomain) : BirthdayListResponse {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(birthdays)
    }

    data class Failure(private val exception: Exception) : BirthdayListResponse {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(exception)
    }
}