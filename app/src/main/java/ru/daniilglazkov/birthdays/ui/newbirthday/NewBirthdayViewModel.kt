package ru.daniilglazkov.birthdays.ui.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.core.Create
import ru.daniilglazkov.birthdays.domain.newbirthday.*
import ru.daniilglazkov.birthdays.ui.newbirthday.about.*
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 23:58
 */
interface NewBirthdayViewModel : BaseSheetViewModel<NewBirthdayUi>, ErrorCommunication.Observe,
    ErrorCommunication.Clear, AboutBirthdateCommunication.Observe, NewBirthdayCommunication.Clear,
    Fetch, Create
{
    fun changeBirthday(name: String, date: LocalDate)
    fun changeDate(date: LocalDate)


    class Base(
        private val interactor: NewBirthdayInteractor,
        private val newBirthdayCommunication: NewBirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val aboutBirthdateCommunication: AboutBirthdateCommunication,
        private val aboutBirthdateDomainToUiMapper: AboutBirthdateDomainToUiMapper,
        private val newBirthdayDomainToUiMapper: NewBirthdayDomainToUiMapper,
        private val newBirthdayUiToDomainMapper: NewBirthdayUiToDomainMapper
    ) : BaseSheetViewModel.Abstract<NewBirthdayUi>(newBirthdayCommunication), NewBirthdayViewModel {
        private val handleSuccess = { newBirthday: NewBirthdayUi ->
            interactor.createNewBirthday(newBirthday.map(newBirthdayUiToDomainMapper))
            clearNewBirthday()
            navigateBack()
        }
        private val handleError = { errorMessage: ErrorMessage ->
            errorCommunication.map(errorMessage)
        }

        override fun fetch() = interactor.latestBirthday().let { birthday: NewBirthdayDomain ->
            newBirthdayCommunication.map(birthday.map(newBirthdayDomainToUiMapper))
        }
        override fun create() = newBirthdayCommunication.validate(handleSuccess, handleError)

        override fun changeDate(date: LocalDate) {
            val aboutBirthdateDomain: AboutBirthdateDomain = interactor.aboutBirthdate(date)
            aboutBirthdateCommunication.map(aboutBirthdateDomain.map(aboutBirthdateDomainToUiMapper))
        }
        override fun changeBirthday(name: String, date: LocalDate) {
            newBirthdayCommunication.filter(name, date) { filteredNewBirthday: NewBirthdayUi ->
                interactor.saveToCache(filteredNewBirthday.map(newBirthdayUiToDomainMapper))
            }
        }
        override fun clearErrorMessage() = errorCommunication.clear()

        override fun clearNewBirthday() = newBirthdayCommunication.clear()

        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
            errorCommunication.observe(owner, observer)
        }
        override fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdateUi>) {
            aboutBirthdateCommunication.observe(owner, observer)
        }
    }
}