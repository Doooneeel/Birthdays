package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.about.AboutBirthdate
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 23:58
 */
interface NewBirthdayViewModel : ErrorCommunication.Observe, ErrorCommunication.Clear, Fetch,
    AboutBirthdateCommunication.Observe
{
    fun changeBirthday(name: String, date: LocalDate)
    fun changeDate(date: LocalDate)
    fun clearNewBirthday()
    fun create()

    class Base(
        private val interactor: NewBirthdayInteractor,
        private val newBirthdayCommunication: NewBirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val aboutBirthdateCommunication: AboutBirthdateCommunication,
        private val newBirthdayDomainToUiMapper: NewBirthdayDomainToUiMapper,
        private val newBirthdayUiToDomainMapper: NewBirthdayUi.Mapper<NewBirthdayDomain>
    ) : BaseSheetViewModel<NewBirthdayUi>(newBirthdayCommunication),
        NewBirthdayViewModel
    {
        private val handleSuccess = { newBirthday: NewBirthdayUi ->
            interactor.create(newBirthday.map(newBirthdayUiToDomainMapper))
            navigateBack()
        }
        private val handleError = { errorMessage: ErrorMessage ->
            errorCommunication.map(errorMessage)
        }

        override fun fetch() {
            val latestNewBirthdayFromCache: NewBirthdayDomain = interactor.latestBirthday()
            newBirthdayCommunication.map(latestNewBirthdayFromCache.map(newBirthdayDomainToUiMapper))
        }
        override fun clearErrorMessage() = errorCommunication.clear()
        override fun clearNewBirthday() = newBirthdayCommunication.clear()

        override fun create() = newBirthdayCommunication.validate(handleSuccess, handleError)

        override fun changeDate(date: LocalDate) = aboutBirthdateCommunication.makeNew(
            interactor.calculateAge(date),
            interactor.calculateDaysToBirthday(date)
        )
        override fun changeBirthday(name: String, date: LocalDate) {
            newBirthdayCommunication.filter(name, date) { newBirthday: NewBirthdayUi ->
                interactor.save(newBirthday.map(newBirthdayUiToDomainMapper))
            }
        }
        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
            errorCommunication.observe(owner, observer)
        }
        override fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdate>) {
            aboutBirthdateCommunication.observe(owner, observer)
        }
    }
}