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
        ) = try {
            successful.invoke(repository.find(birthdayId))
        } catch (exception: Exception) {
            exception.printStackTrace()
            onFailure.invoke()
        }
        override fun delete(id: Int) = repository.delete(id)
    }
}