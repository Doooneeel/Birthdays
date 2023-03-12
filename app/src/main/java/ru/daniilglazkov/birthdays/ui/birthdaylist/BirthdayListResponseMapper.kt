package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListResponse
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.BirthdayListRecyclerState
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerState
import ru.daniilglazkov.birthdays.ui.core.HandleError

/**
 * @author Danil Glazkov on 18.02.2023, 19:15
 */
interface BirthdayListResponseMapper : BirthdayListResponse.Mapper<Unit> {

    class Base(
        private val communications: BirthdayListCommunications.Update,
        private val toUiMapper: BirthdayListDomainToItemsUiMapper,
        private val toChipsMapper: BirthdayListDomainToChipsMapper,
        private val birthdayListRecyclerState: BirthdayListRecyclerState,
        private val handleError: HandleError
    ) : BirthdayListResponseMapper {

        override fun map(birthdays: BirthdayListDomain) {

            val birthdayItemUiList: BirthdayItemUiList = birthdays.map(toUiMapper)
            val chipListUi: ChipListUi = birthdays.map(toChipsMapper)

            communications.putBirthdays(birthdayItemUiList)
            communications.putRecyclerState(birthdayListRecyclerState.newState(birthdays))
            communications.putChips(chipListUi)
        }

        override fun map(exception: Exception) {
            communications.putChips(ChipListUi.Empty)

            communications.putRecyclerState(RecyclerState.Disable)

            communications.putBirthdays(
                BirthdayItemUiList.Message(handleError.handle(exception))
            )
        }
    }
}