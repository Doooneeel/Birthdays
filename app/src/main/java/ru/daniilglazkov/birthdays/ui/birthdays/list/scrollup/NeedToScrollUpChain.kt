package ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain

/**
 * @author Danil Glazkov on 30.08.2022, 22:33
 */
class NeedToScrollUpChain(
    private val base: NeedToScrollUp,
    private val next: NeedToScrollUp,
) : NeedToScrollUp {
    override fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain): Boolean {
        return base.needToScrollUp(before, after) && next.needToScrollUp(before, after)
    }
}