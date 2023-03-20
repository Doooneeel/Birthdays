package ru.daniilglazkov.birthdays.ui.newbirthday

import androidx.lifecycle.viewModelScope
import ru.daniilglazkov.birthdays.domain.core.ChangeDate
import ru.daniilglazkov.birthdays.domain.core.Create
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.core.text.filter.TextFilter
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidateMapper
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidationResult
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 23:58
 */
interface NewBirthdayViewModel : NewBirthdayCommunications.Observe, ErrorCommunication.Hide,
    NewBirthdayCommunication.Clear, Fetch, Create, ChangeDate
{
    fun changeBirthday(name: String, date: LocalDate)


    class Base(
        private val interactor: NewBirthdayInteractor,
        private val communications: NewBirthdayCommunications.Mutable,
        sheetCommunication: SheetCommunication,
        private val mapperToDomain: NewBirthdayUiToDomainMapper,
        private val validateMapper: NewBirthdayValidateMapper,
        private val handleRequest: HandleNewBirthdayRequest,
        private val nameFilter: TextFilter,
        private val dispatchers: CoroutineDispatchers
    ) : BaseSheetViewModel.Abstract(
        sheetCommunication
    ) , NewBirthdayViewModel,
        NewBirthdayCommunications.Observe by communications
    {
        private val handleValidationResult = object : NewBirthdayValidationResult.Mapper<Unit> {
            override fun map(message: ErrorMessage) = communications.putError(message)

            override fun map(birthday: NewBirthdayUi) {
                dispatchers.launchBackground(viewModelScope) {
                    interactor.createNewBirthday(birthday.map(mapperToDomain))
                }
                clearNewBirthday()
                navigateBack()
            }
        }

        override fun fetch() = handleRequest.handle(viewModelScope) {
            interactor.latestBirthday()
        }

        override fun changeDate(date: LocalDate) = handleRequest.handleBirthdate(viewModelScope) {
            interactor.dateOfBirthInfo(date)
        }

        override fun changeBirthday(name: String, date: LocalDate) {
            val filteredNewBirthdayUi = NewBirthdayUi.Base(nameFilter.filter(name), date)
            communications.putNewBirthday(filteredNewBirthdayUi)

            dispatchers.launchBackground(viewModelScope) {
                interactor.saveToCache(filteredNewBirthdayUi.map(mapperToDomain))
            }
        }

        override fun create() {
            val validationResult = communications.validateNewBirthday(validateMapper)
            validationResult.map(handleValidationResult)
        }

        override fun hideErrorMessage() = communications.hideErrorMessage()

        override fun clearNewBirthday() = communications.clearNewBirthday()
    }
}