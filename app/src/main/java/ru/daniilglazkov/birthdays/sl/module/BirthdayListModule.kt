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
import ru.daniilglazkov.birthdays.ui.birthdaylist.*
import ru.daniilglazkov.birthdays.ui.core.QueryCommunication
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup.NeedToScrollUp
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup.NeedToScrollUpBirthdayList
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup.NeedToScrollUpChain
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerStateCommunication

/**
 * @author Danil Glazkov on 10.06.2022, 03:20
 */
class BirthdayListModule(
    private val coreModule: CoreModule,
    private val dateModule: DateModule,
    private val repository: BirthdayListRepository,
    private val zodiacGroupClassification: ZodiacGroupClassification,
    private val showStrategyInteractor: ShowModeInteractor,
) : Module<BirthdayListViewModel.Base> {
    override fun viewModel(): BirthdayListViewModel.Base {
        val resources = coreModule.resourcesManager()
        val now = dateModule.provideCurrentDate()

        val birthdayListDomainToItemsUiMapper = BirthdayListDomainToItemsUiMapper.Base(
            BirthdayDomainToItemUiMapper.Factory(
                BirthdayDomainToItemUiMapperFactory.Base(resources,
                    dateModule.dateDifferenceNextEvent(),
                    dateModule.eventIsToday(),
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
        val birthdaysDomainToSearchMapper = BirthdayListDomainToSearchMapper.Base(
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
                    dateModule.provideNextEvent(),
                    now
                )
            ),
            birthdaysDomainToSearchMapper
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
