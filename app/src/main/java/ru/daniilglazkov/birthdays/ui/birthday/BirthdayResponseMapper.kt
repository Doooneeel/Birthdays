package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayResponse
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacsDomain
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.HandleError
import ru.daniilglazkov.birthdays.ui.core.SheetCommunication
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsDomainToUiMapper

/**
 * @author Danil Glazkov on 17.02.2023, 19:18
 */
interface BirthdayResponseMapper : BirthdayResponse.Mapper<Unit> {

    class Base(
        private val communications: BirthdayCommunications.Update,
        private val birthdayDomainToUiMapper: BirthdayDomainToUiMapper,
        private val zodiacDomainToUiMapper: ZodiacsDomainToUiMapper,
        private val sheetCommunication: SheetCommunication,
        private val handleError: HandleError,
    ) : BirthdayResponseMapper {

        override fun map(birthday: BirthdayDomain, zodiacs: ZodiacsDomain) {
            communications.putBirthday(birthday.map(birthdayDomainToUiMapper))
            communications.putZodiacs(zodiacs.map(zodiacDomainToUiMapper))
        }

        override fun map(exception: Exception) {
            sheetCommunication.put(Unit)

            val errorMessage = ErrorMessage.Base(handleError.handle(exception))
            communications.putError(errorMessage)
        }
    }
}