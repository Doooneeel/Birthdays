package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthdaylist.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.search.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.TransformBirthdayListFactory
import ru.daniilglazkov.birthdays.domain.core.Repository
import ru.daniilglazkov.birthdays.domain.core.text.NormalizeQuery
import ru.daniilglazkov.birthdays.domain.datetime.DateTextFormat
import ru.daniilglazkov.birthdays.domain.settings.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeGroups
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdaylist.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.BirthdayListRecyclerState
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerStateCommunication
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdaySearchQueryCommunication
import ru.daniilglazkov.birthdays.ui.core.HandleError
import ru.daniilglazkov.birthdays.domain.core.text.AddDelimiter
import ru.daniilglazkov.birthdays.sl.module.datetime.DateTimeModule
import ru.daniilglazkov.birthdays.sl.module.zodiac.ZodiacModule

/**
 * @author Danil Glazkov on 10.06.2022, 03:20
 */
class BirthdayListModule(
    private val coreModule: CoreModule,
    private val dateTimeModule: DateTimeModule,
    private val zodiacModule: ZodiacModule,
    private val repository: BirthdayListRepository,
    private val settingsRepository: Repository.Read<SettingsDomain>,
) : Module<BirthdayListViewModel.Base> {

    override fun viewModel(): BirthdayListViewModel.Base {

        val nextEvent = dateTimeModule.provideNextEvent()
        val resources = coreModule.manageResources()
        val now = dateTimeModule.provideCurrentDate()
        val locale = coreModule.locale()

        val birthdayListDomainToItemsUiMapper = BirthdayListDomainToItemsUiMapper.Base(
            BirthdayDomainToItemUiMapper.Factory(
                nextEvent,
                dateTimeModule.provideEventIsToday(),
                resources,
                now,
            )
        )
        val zodiacToQuery = ZodiacDomain.Mapper.ToQuery(locale)
        val normalizeQuery = NormalizeQuery.Base()

        val dateQueryMapper = BirthdayDomainToQueryMapper.Date(
            DateTextFormat.Month(locale),
            DateTextFormat.DayOfWeek(locale),
            locale
        )

        val birthdayMatchesQuery = BirthdayMatchesQuery.Group(
            BirthdayMatchesQuery.IncompleteMatch(
                BirthdayDomainToQueryMapper.Name(normalizeQuery)
            ),
            BirthdayMatchesQuery.IncompleteMatch(dateQueryMapper),
            BirthdayMatchesQuery.IncompleteMatch(
                BirthdayDomainToQueryMapper.WithNextEvent(
                    nextEvent,
                    dateQueryMapper
                )
            ),
            BirthdayMatchesQuery.IncompleteMatch(
                BirthdayDomainToQueryMapper.ZodiacMapper(
                    zodiacModule.provideGreekMapper(),
                    zodiacToQuery
                )
            ),
            BirthdayMatchesQuery.IncompleteMatch(
                BirthdayDomainToQueryMapper.ZodiacMapper(
                    zodiacModule.provideChineseMapper(),
                    zodiacToQuery
                )
            )
        )

        val interactor = BirthdayListInteractor.Base(
            repository,
            ModifyBirthdayList.Base(
                settingsRepository,
                HandleSettingsDataRequest.Base(),
                TransformBirthdayListFactory.Base(
                    zodiacModule.provideGreekZodiacGroup(),
                    AgeGroups.Base(),
                    nextEvent,
                    locale,
                    now
                )
            ),
            BirthdaySearch.Base(birthdayMatchesQuery, normalizeQuery),
            HandleBirthdayListDataRequest.Base()
        )

        val birthdayListDomainToChips = BirthdayListDomainToChipsMapper.Base(
            AddDelimiter.Colon(),
            BirthdayCheckMapper.IsHeader,
            BirthdayDomainToChipMapper.Base(),
            resources
        )

        val recyclerState = BirthdayListRecyclerState.Base(
            NeedToScrollUpChain(
                NeedToScrollUp.ListsNotMatch(),
                NeedToScrollUp.ChangedToOneElement(),
            )
        )
        val communications = BirthdayListCommunications.Base(
            BirthdayListCommunication.Base(),
            BirthdayChipCommunication.Base(),
            BirthdaySearchQueryCommunication.Base(),
            RecyclerStateCommunication.Base(),
        )

        val birthdayListResponseMapper = BirthdayListResponseMapper.Base(
            communications,
            birthdayListDomainToItemsUiMapper,
            birthdayListDomainToChips,
            recyclerState,
            HandleError.Base(resources)
        )

        return BirthdayListViewModel.Base(
            interactor,
            communications,
            HandleBirthdayListRequest.Base(
                coreModule.dispatchers(),
                birthdayListResponseMapper
            ),
            coreModule.navigation(),
        )
    }
}
