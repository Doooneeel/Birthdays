package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.core.Delete
import ru.daniilglazkov.birthdays.domain.core.Find
import ru.daniilglazkov.birthdays.domain.zodiac.*

/**
 * @author Danil Glazkov on 12.06.2022, 20:51
 */
interface BirthdayInteractor : Delete.Suspend, Find.Suspend<BirthdayResponse> {

    class Base(
        private val repository: BirthdayListRepository,
        private val handleRequest: HandleBirthdayDataRequest,
        private val greekZodiacMapper: BirthdayZodiacMapper,
        private val chineseZodiacMapper: BirthdayZodiacMapper
    ) : BirthdayInteractor {

        override suspend fun find(id: Int) = handleRequest.handle {
            val birthday: BirthdayDomain = repository.find(id)

            val zodiacs: ZodiacsDomain = ZodiacsDomain.Base(
                birthday.map(greekZodiacMapper),
                birthday.map(chineseZodiacMapper)
            )
            BirthdayResponse.Success(birthday, zodiacs)
        }

        override suspend fun delete(id: Int) = repository.delete(id)
    }
}