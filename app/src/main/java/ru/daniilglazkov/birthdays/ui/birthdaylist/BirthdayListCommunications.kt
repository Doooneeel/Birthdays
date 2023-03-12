package ru.daniilglazkov.birthdays.ui.birthdaylist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListResponse
import ru.daniilglazkov.birthdays.domain.birthdaylist.FetchBirthdays
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.BirthdayChipCommunication
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipListUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerState
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerStateCommunication
import ru.daniilglazkov.birthdays.ui.core.view.recycler.RecyclerViewScroll
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode

/**
 * @author Danil Glazkov on 24.02.2023, 1:36
 */
interface BirthdayListCommunications  {

    interface Observe : RecyclerStateCommunication.Observe, BirthdayChipCommunication.Observe,
        BirthdayListCommunication.Observe

    interface Update : RecyclerStateCommunication.Put, BirthdayListCommunication.Put,
        BirthdayChipCommunication.Put, BirthdaySearchQueryCommunication.Put,
        RecyclerViewScroll.Smooth
    {
        suspend fun fetchBirthdays(fetchBirthdays: FetchBirthdays): BirthdayListResponse
    }

    interface Mutable : Observe, Update


    class Base(
        private val birthdayListCommunication: BirthdayListCommunication,
        private val chipCommunication: BirthdayChipCommunication,
        private val queryCommunication: BirthdaySearchQueryCommunication,
        private val recyclerStateCommunication: RecyclerStateCommunication,
    ) : Mutable {

        override fun smoothScroll(position: Int) = recyclerStateCommunication.put(
            RecyclerState.Base(ScrollMode.Animated(position))
        )

        override fun putQuery(query: CharSequence) = queryCommunication.put(query)

        override suspend fun fetchBirthdays(fetchBirthdays: FetchBirthdays): BirthdayListResponse =
            queryCommunication.executeQuery(fetchBirthdays)

        override fun putRecyclerState(state: RecyclerState) = recyclerStateCommunication.put(state)

        override fun putChips(chips: ChipListUi) = chipCommunication.put(chips)

        override fun putBirthdays(birthdays: BirthdayItemUiList) =
            birthdayListCommunication.put(birthdays)

        override fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerState>) =
            recyclerStateCommunication.observe(owner, observer)

        override fun observeChips(owner: LifecycleOwner, observer: Observer<ChipListUi>) =
            chipCommunication.observe(owner, observer)

        override fun observeBirthdayList(owner: LifecycleOwner, observer: Observer<BirthdayItemUiList>) =
            birthdayListCommunication.observe(owner, observer)
    }
}