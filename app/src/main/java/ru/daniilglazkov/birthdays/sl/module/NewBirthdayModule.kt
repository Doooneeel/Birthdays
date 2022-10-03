package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.HandleException
import ru.daniilglazkov.birthdays.core.resources.ResourceManager
import ru.daniilglazkov.birthdays.data.main.ProvideNewBirthdayAccess
import ru.daniilglazkov.birthdays.data.newbirthday.BaseNewBirthdayRepository
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayDomainToDataMapper
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayUi
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayViewModel
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.textfilter.TextFilterChain
import ru.daniilglazkov.birthdays.ui.core.textfilter.TextFilterTrim
import ru.daniilglazkov.birthdays.ui.core.textfilter.TextFilterWhitespaces
import ru.daniilglazkov.birthdays.ui.core.validate.ValidateChain
import ru.daniilglazkov.birthdays.ui.core.validate.ValidateFirstCharIsLetter
import ru.daniilglazkov.birthdays.ui.core.validate.ValidateMinLength
import ru.daniilglazkov.birthdays.ui.core.validate.ValidateNotEmpty
import java.time.LocalDate

/**
 * @author Danil Glazkov on 19.06.2022, 15:01
 */
class NewBirthdayModule(
    private val resourceManager: ResourceManager,
    private val database: ProvideNewBirthdayAccess,
    private val repository: BirthdayListRepository,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<NewBirthdayViewModel.Base> {

    private val validate = ValidateChain(
        ValidateNotEmpty(
            resourceManager.string(R.string.empty_name_error_message)
        ),
        ValidateChain(
            ValidateFirstCharIsLetter(
                resourceManager.string(R.string.first_char_is_not_letter_error_message)
            ),
            ValidateMinLength(
                resourceManager.number(R.integer.name_min_length),
                resourceManager.string(R.string.name_min_length_error_message)
            )
        )
    )
    override fun viewModel() = NewBirthdayViewModel.Base(
        interactor = NewBirthdayInteractor.Base(
            repository,
            BaseNewBirthdayRepository(
                NewBirthdayCacheDataSource.Base(
                    database,
                    NewBirthdayData.Mapper.ToDatabaseModel(),
                    HandleException.Empty()
                ),
                NewBirthdayData.Mapper.ToDomain(),
                NewBirthdayDomainToDataMapper.Base()
            ),
            NewBirthdayDomain.Mapper.Create(),
            DateDifference.NextEventInDays(nextEvent),
            DateDifference.YearsPlusOne(),
            now
        ),
        communication = NewBirthdayCommunication.Base(),
        errorCommunication = ErrorCommunication.Base(),

        nameFilter = TextFilterChain(
            TextFilterTrim(),
            TextFilterWhitespaces()
        ),
        validate = validate,
        toUi = NewBirthdayDomainToUiMapper.Base(),
        toDomain = NewBirthdayUi.Mapper.ToDomain(),
        aboutBirthdateCommunication = AboutBirthdateCommunication.Base(),
        provideString = resourceManager
    )
}