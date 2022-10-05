package ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo

import android.util.Log
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
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 21:49
 */
interface BirthdayViewModel : ErrorCommunication.Observe, Fetch {

    fun init(isFirstRun: Boolean, id: Int)
    fun changeStatus(status: Boolean)
    fun dismiss()

    class Base(
        private val interactor: BirthdayCompleteInfoInteractor,
        private val communication: BirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val toUi: BirthdayDomainToUiMapper,
        private val provideString: ProvideString
    ) : BaseSheetViewModel<BirthdayUi>(communication), BirthdayViewModel {
        private var birthdayId: Int = UNKNOWN_ID
        private var needToRemoved = false

        private val handleError = {
            val errorMessage = ErrorMessage.Base(provideString.string(R.string.element_removed))
            errorCommunication.map(errorMessage)
            navigateBack()
        }
        private val handleSuccess = { birthday: BirthdayDomain ->
            communication.map(birthday.map(toUi))
        }

        override fun init(isFirstRun: Boolean, id: Int) {
            if (isFirstRun) birthdayId = id
        }
        override fun changeStatus(status: Boolean) {
            needToRemoved = status
        }
        override fun fetch() = interactor.find(birthdayId, handleSuccess, handleError)

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
            errorCommunication.observe(owner, observer)
        }
        override fun dismiss() {
            if (needToRemoved && birthdayId != UNKNOWN_ID) {
                interactor.delete(birthdayId)
            }
        }
        companion object {
            private const val UNKNOWN_ID: Int = -1
        }
    }
}