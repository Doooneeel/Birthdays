package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.data.newbirthday.*
import ru.daniilglazkov.birthdays.data.newbirthday.cache.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.newbirthday.HandleNewBirthdayRepositoryResponse
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.newbirthday.*
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources
import ru.daniilglazkov.birthdays.ui.core.textfilter.*
import ru.daniilglazkov.birthdays.ui.core.validate.*

/**
 * @author Danil Glazkov on 19.06.2022, 15:01
 */
class NewBirthdayModule(
    private val manageResources: ManageResources,
    private val dateModule: DateModule,
    private val newBirthdayDao: NewBirthdayDao,
    private val repository: BirthdayListRepository,
) : Module<NewBirthdayViewModel.Base> {
    override fun viewModel(): NewBirthdayViewModel.Base {
        val now = dateModule.provideCurrentDate()

        val validate = ValidateChain(
            ValidateNotEmpty(
                manageResources.string(R.string.empty_name_error_message)
            ),
            ValidateChain(
                ValidateFirstCharIsLetter(
                    manageResources.string(R.string.first_char_is_not_letter_error_message)
                ),
                ValidateMinLength(
                    manageResources.number(R.integer.name_min_length),
                    manageResources.string(R.string.name_min_length_error_message)
                )
            )
        )
        val interactor = NewBirthdayInteractor.Base(
            repository,
            BaseNewBirthdayRepository(
                NewBirthdayCacheDataSource.Base(
                    newBirthdayDao,
                    NewBirthdayDataToCacheMapper.Base(),
                ),
                NewBirthdayDataToDomainMapper.Base(),
                NewBirthdayDomainToDataMapper.Base()
            ),
            HandleNewBirthdayRepositoryResponse.Base(now),
            dateModule.dateDifferenceNextEvent(),
            DateDifference.TurnsYearsOld(now),
        )
        val nameFilter = TextFilterChain(TextFilterTrim(), TextFilterWhitespaces())

        return NewBirthdayViewModel.Base(
            interactor,
            NewBirthdayCommunication.Base(validate, nameFilter),
            ErrorCommunication.Base(manageResources),
            AboutBirthdateCommunication.Base(),
            AboutBirthdateDomainToUiMapper.Base(manageResources),
            NewBirthdayDomainToUiMapper.Base(),
            NewBirthdayUiToDomainMapper.Base()
        )
    }
}