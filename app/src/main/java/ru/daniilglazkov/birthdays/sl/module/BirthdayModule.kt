package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayInteractor
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayDomainToUiMapperFactory
import ru.daniilglazkov.birthdays.ui.birthdayinfo.BirthdayViewModel
import ru.daniilglazkov.birthdays.ui.core.DeleteStateCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import java.time.LocalDate

/**
 * @author Danil Glazkov on 12.06.2022, 20:45
 */
class BirthdayModule(
    private val coreModule: CoreModule,
    private val repository: BirthdayListRepository,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdayViewModel.Base> {

    override fun viewModel(): BirthdayViewModel.Base {
        val interactor = BirthdayInteractor.Base(repository)

        val sheetBirthdayDomainToUiMapper = BirthdayDomainToUiMapper.Factory(
            BirthdayDomainToUiMapperFactory.Sheet(coreModule.resourcesManager(), nextEvent, now)
        )
        return BirthdayViewModel.Base(
            interactor,
            BirthdayCommunication.Base(),
            ErrorCommunication.Base(coreModule.resourcesManager()),
            DeleteStateCommunication.Base(),
            sheetBirthdayDomainToUiMapper
        )
    }
}