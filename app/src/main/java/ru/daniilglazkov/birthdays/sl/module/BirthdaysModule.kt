package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListInteractor
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeInteractor
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.TransformBirthdayListFactory
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdayChipCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUp
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUpBirthdayList
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUpChain
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.ScrollUpCommunication
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 03:20
 */
class BirthdaysModule(
    private val coreModule: CoreModule,
    private val repository: BirthdayListRepository,
    private val showStrategyInteractor: BirthdayListShowModeInteractor,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdaysViewModel> {

    override fun viewModel() = BirthdaysViewModel(
        interactor = BirthdayListInteractor.Base(
            repository,
            showStrategyInteractor,
            ShowModeDomain.Mapper.Transform(
                TransformBirthdayListFactory.Base(nextEvent)
            ),
        ),
        communication = BirthdaysCommunication.Base(),
        chipCommunication = BirthdayChipCommunication.Base(),
        scrollUpCommunication = ScrollUpCommunication.Base(
            NeedToScrollUpBirthdayList.Base(
                NeedToScrollUpChain(
                    NeedToScrollUp.ListsMatch(),
                    NeedToScrollUp.AddOrDelete(
                        BirthdayListDomain.CountMapper.CountWithoutHeaders()
                    ),
                )
            )
        ),
        navigation = coreModule.navigation(),
        birthdayListDomainToUi = BirthdayListDomainToUiMapper.Base(
            BirthdayDomainToUiMapper.Base(
                BirthdateTextFormat.Age(
                    coreModule.resourcesManager(),
                    now
                ),
                BirthdateTextFormat.Empty(),
                BirthdateTextFormat.DaysUntilBirthday(
                    coreModule.resourcesManager(),
                    nextEvent,
                    now
                ),
                DateDifference.YearsPlusOne(),
                now
            )
        ),
        birthdayListDomainToChips = BirthdayListDomainToChipsMapper.OnlyName(
            BirthdayDomain.CheckMapper.IsHeader(),
        ),
        provideString = coreModule.resourcesManager()
    )
}
