package ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayCompleteInfoInteractor
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 21:49
 */
interface BirthdayViewModel : ErrorCommunication.Observe, Fetch, Completion {

    fun init(isFirstRun: Boolean, id: Int)
    fun changeDeleteState(isDelete: Boolean)

    class Base(
        private val interactor: BirthdayCompleteInfoInteractor,
        private val birthdayCommunication: BirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val deleteStateCommunication: DeleteStateCommunication,
        private val birthdayDomainToUiMapper: BirthdayDomainToUiMapper,
        private val resources: ProvideString
    ) : BaseSheetViewModel<BirthdayUi>(birthdayCommunication), BirthdayViewModel {
        private var id: Int = -1

        private val handleError = {
            val errorMessage = ErrorMessage.Base(resources.string(R.string.element_removed))
            errorCommunication.map(errorMessage)
            navigateBack()
        }
        private val handleSuccess = { birthday: BirthdayDomain ->
            birthdayCommunication.map(birthday.map(birthdayDomainToUiMapper))
        }

        override fun init(isFirstRun: Boolean, id: Int) {
            if (isFirstRun) this.id = id
        }
        override fun fetch() = interactor.find(id, handleSuccess, handleError)

        override fun changeDeleteState(isDelete: Boolean) = deleteStateCommunication.map(isDelete)

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
            errorCommunication.observe(owner, observer)
        }
        override fun complete() = deleteStateCommunication.handleTrue {
            interactor.delete(id)
        }
    }
}