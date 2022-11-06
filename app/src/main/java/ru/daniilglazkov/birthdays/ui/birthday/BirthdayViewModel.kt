package ru.daniilglazkov.birthdays.ui.birthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayInteractor
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayZodiacsDomain
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 21:49
 */
interface BirthdayViewModel : BaseSheetViewModel<BirthdayUi>, ErrorCommunication.Observe,
    DeleteStateCommunication.Observe, Fetch,
    BirthdayZodiacsCommunication.Observe, Completion
{
    fun init(isFirstRun: Boolean, id: Int)
    fun changeDeleteState(isDelete: Boolean)


    class Base(
        private val interactor: BirthdayInteractor,
        private val birthdayCommunication: BirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val zodiacsUiCommunication: BirthdayZodiacsCommunication,
        private val deleteStateCommunication: DeleteStateCommunication,
        private val birthdayDomainToUiMapper: BirthdayDomainToUiMapper,
        private val zodiacDomainToUiMapper: BirthdayZodiacsDomainToUiMapper,
    ) : BaseSheetViewModel.Abstract<BirthdayUi>(birthdayCommunication), BirthdayViewModel {
        private var id: Int = -1

        private val handleError = {
            errorCommunication.throwErrorMessage(R.string.element_removed)
            navigateBack()
        }
        private val handleSuccess = { birthday: BirthdayDomain ->
            val zodiacsDomain: BirthdayZodiacsDomain = interactor.zodiac(birthday)

            zodiacsUiCommunication.map(zodiacsDomain.map(zodiacDomainToUiMapper))
            birthdayCommunication.map(birthday.map(birthdayDomainToUiMapper))
        }

        override fun init(isFirstRun: Boolean, id: Int) {
            if (isFirstRun) this.id = id
        }
        override fun fetch() = interactor.find(id, handleSuccess, handleError)

        override fun changeDeleteState(isDelete: Boolean) = deleteStateCommunication.map(isDelete)

        override fun observeBirthdayZodiacs(owner: LifecycleOwner, observer: Observer<BirthdayZodiacsUi>) {
            zodiacsUiCommunication.observe(owner, observer)
        }
        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
            errorCommunication.observe(owner, observer)
        }
        override fun observeDeleteState(owner: LifecycleOwner, observer: Observer<Boolean>) {
            deleteStateCommunication.observe(owner, observer)
        }
        override fun complete() = deleteStateCommunication.handleTrue {
            interactor.deleteBirthday(id)
        }
    }
}