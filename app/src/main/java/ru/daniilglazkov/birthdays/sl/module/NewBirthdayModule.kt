package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.data.newbirthday.*
import ru.daniilglazkov.birthdays.data.newbirthday.cache.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.datetime.DateDifference
import ru.daniilglazkov.birthdays.domain.newbirthday.HandleNewBirthdayDataRequest
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.domain.newbirthday.FetchDateOfBirthInfo
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.sl.module.datetime.DateTimeModule
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.SheetCommunication
import ru.daniilglazkov.birthdays.ui.core.text.filter.*
import ru.daniilglazkov.birthdays.ui.newbirthday.*
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.NewBirthdayValidateMapper
import ru.daniilglazkov.birthdays.ui.newbirthday.validation.ValidateName

/**
 * @author Danil Glazkov on 19.06.2022, 15:01
 */
class NewBirthdayModule(
    private val coreModule: CoreModule,
    private val dateTimeModule: DateTimeModule,
    private val newBirthdayDao: NewBirthdayDao,
    private val repository: BirthdayListRepository,
) : Module<NewBirthdayViewModel.Base> {

    override fun viewModel(): NewBirthdayViewModel.Base {

        val now = dateTimeModule.provideCurrentDate()
        val resources = coreModule.manageResources()
        val nameFilter = TextFilterChain(TextFilterTrim(), TextFilterWhitespaces())
        val dispatchers = coreModule.dispatchers()
        val nextEvent = dateTimeModule.provideNextEvent()

        val interactor = NewBirthdayInteractor.Base(
            repository,
            BaseNewBirthdayRepository(
                NewBirthdayCacheDataSource.Base(
                    newBirthdayDao,
                    NewBirthdayDataToCacheMapper.Base()
                ),
                NewBirthdayDataToDomainMapper.Base(),
                NewBirthdayDomainToDataMapper.Base()
            ),
            HandleNewBirthdayDataRequest.Base(now),
            FetchDateOfBirthInfo.Base(
                DateDifference.Years.TurnsYearsOld(now),
                DateDifference.Days.NextEvent(nextEvent, now),
            ),
        )

        val communications = NewBirthdayCommunications.Base(
            NewBirthdayCommunication.Base(),
            ErrorCommunication.Base(),
            AboutBirthdateCommunication.Base(),
            now
        )

        val handleNewBirthdayRequest = HandleNewBirthdayRequest.Base(
            communications,
            NewBirthdayDomainToUiMapper.Base(),
            HandleAboutBirthdateRequest.Base(
                communications,
                AboutBirthdateDomainToUiMapper.Base(resources),
                dispatchers
            ),
            dispatchers
        )

        return NewBirthdayViewModel.Base(
            interactor,
            communications,
            SheetCommunication.Base(),
            NewBirthdayUiToDomainMapper.Base(),
            NewBirthdayValidateMapper.Base(ValidateName(resources)),
            handleNewBirthdayRequest,
            nameFilter,
            dispatchers,
        )
    }
}