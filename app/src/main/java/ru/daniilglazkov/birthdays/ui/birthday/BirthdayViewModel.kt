package ru.daniilglazkov.birthdays.ui.birthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayInteractor
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsUi

/**
 * @author Danil Glazkov on 10.06.2022, 21:49
 */
interface BirthdayViewModel : BirthdayCommunications.Observe, Complete, Fetch {

    fun init(isFirstRun: Boolean, id: Int)

    fun changeDeleteState(state: Boolean)


    class Base(
        private val interactor: BirthdayInteractor,
        private val communications: BirthdayCommunications.Mutable,
        sheetCommunication: SheetCommunication,
        private val handleBirthdaysRequest: HandleBirthdayRequest,
        private val dispatchers: CoroutineDispatchers
    ) : BaseSheetViewModel.Abstract(sheetCommunication), BirthdayViewModel {

        override fun init(isFirstRun: Boolean, id: Int) {
            if (isFirstRun) communications.putId(id)
        }

        override fun fetch() = handleBirthdaysRequest.handle(viewModelScope) {
            communications.find(interactor)
        }

        override fun complete() = communications.handleTrueDeleteState {
            dispatchers.launchBackground(viewModelScope) {
                communications.delete(interactor)
            }
        }

        override fun changeDeleteState(state: Boolean) = communications.putDeleteState(state)

        override fun observeBirthday(owner: LifecycleOwner, observer: Observer<BirthdayUi>) =
            communications.observeBirthday(owner, observer)

        override fun observeZodiacs(owner: LifecycleOwner, observer: Observer<ZodiacsUi>) =
            communications.observeZodiacs(owner, observer)

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) =
            communications.observeError(owner, observer)

        override fun observeDeleteState(owner: LifecycleOwner, observer: Observer<Boolean>) =
            communications.observeDeleteState(owner, observer)
    }
}