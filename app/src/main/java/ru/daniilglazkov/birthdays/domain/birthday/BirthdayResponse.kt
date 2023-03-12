package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacsDomain

/**
 * @author Danil Glazkov on 17.02.2023, 19:19
 */
interface BirthdayResponse {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {

        fun map(birthday: BirthdayDomain, zodiacs: ZodiacsDomain): T

        fun map(exception: Exception): T

    }


    data class Success(
        private val birthday: BirthdayDomain,
        private val zodiacs: ZodiacsDomain
    ) : BirthdayResponse {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(birthday, zodiacs)
    }

    data class Failure(private val exception: Exception) : BirthdayResponse {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(exception)
    }
}