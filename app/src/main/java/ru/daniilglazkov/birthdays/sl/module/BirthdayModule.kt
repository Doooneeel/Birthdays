package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayInteractor
import ru.daniilglazkov.birthdays.domain.birthday.HandleBirthdayDataRequest
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthday.*
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.core.text.format.DaysToEventTextFormat
import ru.daniilglazkov.birthdays.ui.zodiac.*

/**
 * @author Danil Glazkov on 12.06.2022, 20:45
 */
class BirthdayModule(
    private val coreModule: CoreModule,
    private val dateModule: DateModule,
    private val zodiacModule: ZodiacModule,
    private val repository: BirthdayListRepository,
) : Module<BirthdayViewModel.Base> {

    override fun viewModel(): BirthdayViewModel.Base {
        val resources = coreModule.manageResources()
        val dispatchers = coreModule.dispatchers()

        val interactor = BirthdayInteractor.Base(repository,
            HandleBirthdayDataRequest.Base(),
            zodiacModule.provideGreekMapper(),
            zodiacModule.provideChineseMapper(),
        )
        val calculateNextEvent = dateModule.provideNextEvent()
        val currentDate = dateModule.provideCurrentDate()

        val zodiacsDomainToUiMapper = ZodiacsDomainToUiMapper.Base(
            ZodiacDomainToUiMapper.Greek(),
            ZodiacDomainToUiMapper.Chinese()
        )

        val sheetBirthdayDomainToUiMapper = BirthdayDomainToUiMapper.Base(
            DateTextFormat.Full(coreModule.locale()),
            DaysToEventTextFormat.OnlyNumbers(resources),
            DateDifference.Years.TurnsYearsOld(currentDate),
            DateDifference.Days.NextEvent(calculateNextEvent, currentDate),
            calculateNextEvent,
            resources,
        )
        val sheetCommunication = SheetCommunication.Base()

        val communications = BirthdayCommunications.Base(
            BirthdayCommunication.Base(),
            BirthdayIdCommunication.Base(),
            ZodiacsCommunication.Base(),
            DeleteStateCommunication.Base(),
            ErrorCommunication.Base()
        )

        val responseMapper = BirthdayResponseMapper.Base(
            communications,
            sheetBirthdayDomainToUiMapper,
            zodiacsDomainToUiMapper,
            sheetCommunication,
            HandleError.Birthday(resources)
        )

        return BirthdayViewModel.Base(
            interactor,
            communications,
            sheetCommunication,
            HandleBirthdayRequest.Base(dispatchers, responseMapper),
            dispatchers
        )
    }
}