package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.Delete

/**
 * @author Danil Glazkov on 12.06.2022, 20:51
 */
interface BirthdayCompleteInfoInteractor : Delete {

    fun find(
        birthdayId: Int,
        successful: (BirthdayDomain) -> Unit,
        onFailure: () -> Unit
    )

    class Base(
        private val repository: BirthdayListRepository,
    ) : BirthdayCompleteInfoInteractor {
        override fun find(
            birthdayId: Int,
            successful: (BirthdayDomain) -> Unit,
            onFailure: () -> Unit
        ) = repository.find(birthdayId).let { birthday ->
            if (birthday is BirthdayDomain.Fail) {
                onFailure.invoke()
            } else {
                successful.invoke(birthday)
            }
        }
        override fun delete(id: Int) = repository.delete(id)
    }
}