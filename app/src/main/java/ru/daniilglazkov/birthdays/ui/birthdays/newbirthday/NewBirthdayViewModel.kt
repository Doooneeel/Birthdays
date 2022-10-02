package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.ui.birthdays.AboutBirthdate
import ru.daniilglazkov.birthdays.ui.birthdays.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.textfilter.TextFilter
import ru.daniilglazkov.birthdays.ui.core.validate.Validate
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 23:58
 */
class NewBirthdayViewModel(
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
    ErrorCommunication.Observe
{
    private val emptyNewBirthday = NewBirthdayUi.Empty()
    private val handleSuccess = { newBirthday: NewBirthdayUi ->
        interactor.create(newBirthday.map(toDomain))
        navigateBack()
    }
    fun fetch() {
        val latestNewBirthdayFromCache: NewBirthdayDomain = interactor.latestBirthday()
        communication.map(latestNewBirthdayFromCache.map(toUi))
    }
    fun clearErrorMessage() = errorCommunication.clear()

    fun clearNewBirthday() = communication.map(emptyNewBirthday)

    fun create() {
        communication.validate(validate, handleSuccess, errorCommunication::map)
    }
    fun changeDate(date: LocalDate) = aboutBirthdateCommunication.map(AboutBirthdate.Base(
        provideString.quantityString(R.plurals.age, interactor.calculateAge(date)),
        provideString.quantityString(R.plurals.day, interactor.calculateDaysToBirthday(date)),
    ))
    fun changeBirthday(name: String, date: LocalDate) {
        val birthday = NewBirthdayUi.Base(nameFilter.filter(name), date)

        interactor.save(birthday.map(toDomain))
        communication.map(birthday)
    }
    override fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>) {
        errorCommunication.observe(owner, observer)
    }
    fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdate>) {
        aboutBirthdateCommunication.observe(owner, observer)
    }
}