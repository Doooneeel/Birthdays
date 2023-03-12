package ru.daniilglazkov.birthdays.ui.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateUi
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidateMapper
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidationResult
import java.time.LocalDate

/**
 * @author Danil Glazkov on 24.02.2023, 1:55
 */
interface NewBirthdayCommunications {

    interface Update : ErrorCommunication.Combo, NewBirthdayCommunication.Combo,
        AboutBirthdateCommunication.Put {

        fun validateNewBirthday(mapper: NewBirthdayValidateMapper): NewBirthdayValidationResult

    }

    interface Observe :
        AboutBirthdateCommunication.Observe,
        NewBirthdayCommunication.Observe,
        ErrorCommunication.Observe


    interface Mutable : Update, Observe


    class Base(
        private val newBirthdayCommunication: NewBirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val aboutBirthdateCommunication: AboutBirthdateCommunication,
        private val now: LocalDate
    ) : Mutable {

        override fun validateNewBirthday(mapper: NewBirthdayValidateMapper) =
            newBirthdayCommunication.map(mapper)

        override fun putError(message: ErrorMessage) = errorCommunication.put(message)

        override fun putNewBirthday(birthday: NewBirthdayUi) = newBirthdayCommunication.put(birthday)

        override fun putAboutBirthdate(about: AboutBirthdateUi) = aboutBirthdateCommunication.put(about)

        override fun hideErrorMessage() = errorCommunication.put(ErrorMessage.Empty)

        override fun clearNewBirthday() = newBirthdayCommunication.put(NewBirthdayUi.Empty(now))

        override fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdateUi>) =
            aboutBirthdateCommunication.observe(owner, observer)

        override fun observeNewBirthday(owner: LifecycleOwner, observer: Observer<NewBirthdayUi>)  =
            newBirthdayCommunication.observe(owner, observer)

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>)  =
            errorCommunication.observe(owner, observer)
    }
}