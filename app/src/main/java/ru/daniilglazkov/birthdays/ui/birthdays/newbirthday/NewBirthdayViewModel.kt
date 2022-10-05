package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.about.AboutBirthdate
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.core.ClearErrorMessage
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.core.textfilter.TextFilter
import ru.daniilglazkov.birthdays.ui.core.validate.Validate
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 23:58
 */
interface NewBirthdayViewModel : ErrorCommunication.Observe, ClearErrorMessage, Fetch {

    fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdate>)
    fun changeBirthday(name: String, date: LocalDate)
    fun changeDate(date: LocalDate)
    fun clearNewBirthday()
    fun create()

    class Base(
        private val interactor: NewBirthdayInteractor,
        private val communication: NewBirthdayCommunication,
        private val errorCommunication: ErrorCommunication,
        private val aboutBirthdateCommunication: AboutBirthdateCommunication,
        private val nameFilter: TextFilter,
        private val validate: Validate,
        private val provideString: ProvideString,
        private val toUi: NewBirthdayDomainToUiMapper,
        private val toDomain: NewBirthdayUi.Mapper<NewBirthdayDomain>
    ) : BaseSheetViewModel<NewBirthdayUi>(communication),
        NewBirthdayViewModel
    {
        private val emptyNewBirthday = NewBirthdayUi.Empty()
        private val handleSuccess = { newBirthday: NewBirthdayUi ->
            interactor.create(newBirthday.map(toDomain))
            navigateBack()
        }
        override fun fetch() {
            val latestNewBirthdayFromCache: NewBirthdayDomain = interactor.latestBirthday()
            communication.map(latestNewBirthdayFromCache.map(toUi))
        }
        override fun clearErrorMessage() = errorCommunication.clear()

        override fun clearNewBirthday() = communication.map(emptyNewBirthday)

        override fun create() {
            communication.validate(validate, handleSuccess, errorCommunication::map)
        }
        override fun changeDate(date: LocalDate) = aboutBirthdateCommunication.map(
            AboutBirthdate.Base(
            provideString.quantityString(R.plurals.age, interactor.calculateAge(date)),
            provideString.quantityString(R.plurals.day, interactor.calculateDaysToBirthday(date)),
        ))
        override fun changeBirthday(name: String, date: LocalDate) {
            val birthday = NewBirthdayUi.Base(nameFilter.filter(name), date)

            interactor.save(birthday.map(toDomain))
            communication.map(birthday)
        }
        override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
            errorCommunication.observe(owner, observer)
        }
        override fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdate>) {
            aboutBirthdateCommunication.observe(owner, observer)
        }
    }

}