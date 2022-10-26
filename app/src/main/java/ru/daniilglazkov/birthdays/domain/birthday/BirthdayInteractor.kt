package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.core.Interactor
import ru.daniilglazkov.birthdays.domain.zodiac.BirthdayDomainToZodiacMapper
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain

/**
 * @author Danil Glazkov on 12.06.2022, 20:51
 */
interface BirthdayInteractor {

    fun find(
        id: Int,
        successful: (BirthdayDomain) -> Unit,
        onFailure: () -> Unit
    )
    fun zodiac(birthdayDomain: BirthdayDomain): ZodiacDomain
    fun deleteBirthday(id: Int)


    class Base(
        private val repository: BirthdayListRepository,
        private val domainToZodiacMapper: BirthdayDomainToZodiacMapper
    ) : Interactor.Abstract(), BirthdayInteractor {
        override fun deleteBirthday(id: Int) = repository.delete(id)

        override fun find(
            id: Int,
            successful: (BirthdayDomain) -> Unit,
            onFailure: () -> Unit,
        ) = handle(successful, onFailure) {
            repository.find(id)
        }
        override fun zodiac(birthdayDomain: BirthdayDomain): ZodiacDomain =
            birthdayDomain.map(domainToZodiacMapper)
    }
}