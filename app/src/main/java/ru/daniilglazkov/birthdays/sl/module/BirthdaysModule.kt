package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthdays.*
import ru.daniilglazkov.birthdays.domain.birthdays.search.*
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.*
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerStateCommunication
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 03:20
 */
class BirthdaysModule(
    private val coreModule: CoreModule,
    private val repository: BirthdayListRepository,
    private val showStrategyInteractor: ShowModeInteractor,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdaysViewModel.Base> {

    override fun viewModel(): BirthdaysViewModel.Base {
        val birthdayMatchesQuery = BirthdayMatchesQueryChain(
            BirthdayMatchesQuery.Name(),
            BirthdayMatchesQueryChain(
                BirthdayMatchesQuery.DateFormat(DateTextFormat.Month()),
                BirthdayMatchesQuery.RawDate()
            )
        )
        val searchWrapper = BirthdayListDomainToSearchMapper.Base(
            birthdayMatchesQuery,
            BirthdayDomain.CheckMapper.IsNotHeader(),
            PrepareQuery.Base(),
        )
        val interactor = BirthdayListInteractor.Base(
            repository,
            showStrategyInteractor,
            ShowModeDomain.Mapper.Transform(
                TransformBirthdayListFactory.Base(nextEvent, coreModule.resourcesManager())
            ),
            searchWrapper
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
        val birthdayListDomainToUi = BirthdayListDomainToUiMapper(
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
