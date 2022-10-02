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
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 21:49
 */
class BirthdayViewModel(
    private val interactor: BirthdayCompleteInfoInteractor,
    private val communication: BirthdayCommunication,
    private val errorCommunication: ErrorCommunication,
    private val toUi: BirthdayDomainToUiMapper,
    private val provideString: ProvideString
) : BaseSheetViewModel<BirthdayUi>(communication), ErrorCommunication.Observe {
    private var birthdayId: Int = -1

    private val handleError = {
        val errorMessage = ErrorMessage.Base(provideString.string(R.string.element_removed))
        errorCommunication.map(errorMessage)
        navigateBack()
    }
    private val handleSuccess = { birthday: BirthdayDomain ->
        communication.map(birthday.map(toUi))
    }
    fun init(firstCall: Boolean, id: Int) {
        if (firstCall) birthdayId = id
    }
    fun fetch() = interactor.find(birthdayId, handleSuccess, handleError)

    fun delete() {
        interactor.delete(birthdayId)
        navigateBack()
    }

    override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
        errorCommunication.observe(owner, observer)
    }
}