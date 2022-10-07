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
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.list.QueryCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUp
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUpBirthdayList
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUpChain
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerStateCommunication
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
) : Module<BirthdaysViewModel.Base> {

    override fun viewModel(): BirthdaysViewModel.Base {
        val interactor = BirthdayListInteractor.Base(
            repository,
            showStrategyInteractor,
            ShowModeDomain.Mapper.Transform(
                TransformBirthdayListFactory.Base(nextEvent, coreModule.resourcesManager())
            )
        )
        val recyclerStateCommunication = RecyclerStateCommunication.Base(
            NeedToScrollUpBirthdayList.Base(
                NeedToScrollUpChain(
                    NeedToScrollUp.ListsMatch(),
                    NeedToScrollUp.AddOrDelete(
                        BirthdayListDomain.CountMapper.CountWithoutHeaders()
                    ),
                )
            )
        )
        val birthdayListDomainToUi = BirthdayListDomainToUiMapper.Base(
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
        )
        val birthdayListDomainToChips = BirthdayListDomainToChipsMapper.Base(
            BirthdayDomainToChipMapper.Base(ChipTextFormat.OnlyName()),
            BirthdayDomain.CheckMapper.IsHeader(),
            coreModule.resourcesManager(),
            ChipTextFormat.NameWithCount()
        )
        return BirthdaysViewModel.Base(
            interactor,
            BirthdaysCommunication.Base(coreModule.resourcesManager()),
            BirthdayChipCommunication.Base(),
            recyclerStateCommunication,
            QueryCommunication.Base(),
            coreModule.navigation(),
            birthdayListDomainToUi,
            birthdayListDomainToChips
        )
    }
}
