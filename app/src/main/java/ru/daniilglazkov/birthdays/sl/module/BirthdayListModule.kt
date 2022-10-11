package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthdaylist.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.search.*
import ru.daniilglazkov.birthdays.domain.showmode.*
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerStateCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.*
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 03:20
 */
class BirthdayListModule(
    private val coreModule: CoreModule,
    private val repository: BirthdayListRepository,
    private val showStrategyInteractor: ShowModeInteractor,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdaysViewModel.Base> {

    override fun viewModel(): BirthdaysViewModel.Base {

        val birthdayListDomainToUiMapper = BirthdayListDomainToUiMapper.Base(
            BirthdayDomainToUiMapper.Factory(
                BirthdayDomainToUiMapperFactory.Base(
                    coreModule.resourcesManager(),
                    nextEvent,
                    now
                )
            )
        )
        val birthdayMatchesQuery = BirthdayMatchesQueryChain(
            BirthdayMatchesQuery.Name(),
            BirthdayMatchesQueryChain(
                BirthdayMatchesQuery.DateFormat(DateTextFormat.Month()),
                BirthdayMatchesQuery.RawDate()
            )
        )
        val searchWrapper = BirthdayListDomainToSearchMapper.Base(
            birthdayMatchesQuery,
            BirthdayCheckMapper.IsNotHeader(),
            PrepareQuery.Base(),
        )
        val interactor = BirthdayListInteractor.Base(
            repository,
            showStrategyInteractor,
            ShowModeDomain.Mapper.Transform(
                TransformBirthdayListFactory.Base(
                    nextEvent,
                    coreModule.resourcesManager()
                )
            ),
            searchWrapper
        )
        val recyclerStateCommunication = RecyclerStateCommunication.Base(
            NeedToScrollUpBirthdayList.Base(
                NeedToScrollUpChain(
                    NeedToScrollUp.ListsMatch(),
                    NeedToScrollUp.AddOrDelete(
                        BirthdayListCountMapper.CountWithoutHeaders()
                    ),
                )
            )
        )
        val birthdayListDomainToChips = BirthdayListDomainToChipsMapper.Base(
            BirthdayDomainToChipMapper.Base(ChipTextFormat.OnlyName()),
            BirthdayCheckMapper.IsHeader(),
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
            birthdayListDomainToUiMapper,
            birthdayListDomainToChips
        )
    }
}
