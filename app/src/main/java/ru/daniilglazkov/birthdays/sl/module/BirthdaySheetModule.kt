package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayCompleteInfoInteractor
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdateTextFormat
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo.DeleteStateCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import java.time.LocalDate

/**
 * @author Danil Glazkov on 12.06.2022, 20:45
 */
class BirthdaySheetModule(
    private val provideString: ProvideString,
    private val repository: BirthdayListRepository,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdayViewModel.Base> {
    override fun viewModel(): BirthdayViewModel.Base {
        val interactor = BirthdayCompleteInfoInteractor.Base(repository)

        val birthdayDomainToUiMapper = BirthdayDomainToUiMapper.Base(
            BirthdateTextFormat.Age(provideString, now),
            BirthdateTextFormat.Date(DateTextFormat.Full()),
            BirthdateTextFormat.DaysUntilBirthdaySheet(provideString, nextEvent, now),
            DateDifference.YearsPlusOne(),
            now
        )
        return BirthdayViewModel.Base(
            interactor,
            BirthdayCommunication.Base(),
            ErrorCommunication.Base(provideString),
            DeleteStateCommunication.Base(),
            birthdayDomainToUiMapper
        )
    }
}