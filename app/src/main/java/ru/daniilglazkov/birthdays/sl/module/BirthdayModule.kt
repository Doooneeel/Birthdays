package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayInteractor
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.domain.zodiac.BirthdayDomainToZodiacMapper
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthday.BirthdateTextFormat
import ru.daniilglazkov.birthdays.ui.birthday.BirthdayCommunication
import ru.daniilglazkov.birthdays.ui.birthday.BirthdayDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthday.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.core.DeleteStateCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacUiCommunication
import java.time.LocalDate

/**
 * @author Danil Glazkov on 12.06.2022, 20:45
 */
class BirthdayModule(
    private val coreModule: CoreModule,
    private val zodiacGroupClassification: ZodiacGroupClassification,
    private val repository: BirthdayListRepository,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdayViewModel.Base> {
    override fun viewModel(): BirthdayViewModel.Base {
        val resources = coreModule.resourcesManager()

        val interactor = BirthdayInteractor.Base(repository,
            BirthdayDomainToZodiacMapper.Base(zodiacGroupClassification)
        )
        val sheetBirthdayDomainToUiMapper = BirthdayDomainToUiMapper.Base(
            BirthdateTextFormat.Age(resources, now),
            BirthdateTextFormat.Date(DateTextFormat.Full()),
            BirthdateTextFormat.DaysToBirthdaySheet(resources, nextEvent, now)
        )
        return BirthdayViewModel.Base(
            interactor,
            BirthdayCommunication.Base(),
            ErrorCommunication.Base(resources),
            ZodiacUiCommunication.Base(),
            sheetBirthdayDomainToUiMapper,
            ZodiacDomainToUiMapper.Base(),
            DeleteStateCommunication.Base(),
        )
    }
}