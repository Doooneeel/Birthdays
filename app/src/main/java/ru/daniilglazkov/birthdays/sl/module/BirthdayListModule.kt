package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthdaylist.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.search.*
import ru.daniilglazkov.birthdays.domain.showmode.*
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.domain.showmode.age.AgeGroupClassification
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListCommunication
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListViewModel
import ru.daniilglazkov.birthdays.ui.core.QueryCommunication
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.recyclerstate.RecyclerStateCommunication
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.scrollup.*
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 03:20
 */
class BirthdayListModule(
    private val coreModule: CoreModule,
    private val repository: BirthdayListRepository,
    private val zodiacGroupClassification: ZodiacGroupClassification,
    private val showStrategyInteractor: ShowModeInteractor,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<BirthdayListViewModel.Base> {
    override fun viewModel(): BirthdayListViewModel.Base {
        val resources = coreModule.resourcesManager()

        val birthdayListDomainToItemsUiMapper = BirthdayListDomainToItemsUiMapper.Base(
            BirthdayDomainToItemUiMapper.Factory(
                BirthdayDomainToItemUiMapperFactory.Base(resources, nextEvent, now)
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
                    zodiacGroupClassification,
                    AgeGroupClassification.Base(),
                    nextEvent,
                    now
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
            BirthdayDomainToChipMapper.Base(),
            BirthdayCheckMapper.IsHeader(),
            resources,
            ChipTextFormat.NameWithCount()
        )
        return BirthdayListViewModel.Base(
            interactor,
            BirthdayListCommunication.Base(resources),
            BirthdayChipCommunication.Base(),
            recyclerStateCommunication,
            QueryCommunication.Base(),
            coreModule.navigation(),
            birthdayListDomainToItemsUiMapper,
            birthdayListDomainToChips
        )
    }
}
