package ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import kotlin.math.abs

/**
 * @author Danil Glazkov on 30.08.2022, 22:30
 */
interface NeedToScrollUp {
    fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain): Boolean

    class ListsMatch : NeedToScrollUp {
        override fun needToScrollUp(
            before: BirthdayListDomain,
            after: BirthdayListDomain
        ) = before.matches(after).not()
    }

    class AddOrDelete(
        private val countWithoutHeaders: BirthdayListDomain.CountMapper
    ) : NeedToScrollUp {
        override fun needToScrollUp(
            before: BirthdayListDomain,
            after: BirthdayListDomain
        ) = abs(before.map(countWithoutHeaders) - after.map(countWithoutHeaders)) != 1
    }

}