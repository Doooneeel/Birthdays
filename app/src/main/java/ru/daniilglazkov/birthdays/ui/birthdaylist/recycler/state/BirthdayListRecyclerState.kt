package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup.NeedToScrollUp
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode

/**
 * @author Danil Glazkov on 30.08.2022, 22:23
 */
interface BirthdayListRecyclerState {

    fun newState(changedList: BirthdayListDomain): RecyclerState


    class Base(private val needToScrollUp: NeedToScrollUp) : BirthdayListRecyclerState {
        private var previousList: BirthdayListDomain = BirthdayListDomain.Base()

        override fun newState(changedList: BirthdayListDomain): RecyclerState {
            val needToScrollUp: Boolean = needToScrollUp.needToScrollUp(previousList, changedList)

            val scrollMode = if (needToScrollUp) ScrollMode.SharpUp else ScrollMode.NoScroll
            val nestedToScroll: Boolean = changedList.asList().isNotEmpty()

            previousList = changedList

            return RecyclerState.Base(scrollMode, nestedToScroll)
        }
    }
}