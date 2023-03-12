package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListCountMapper
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomainCompareMapper
import kotlin.math.abs

/**
 * @author Danil Glazkov on 30.08.2022, 22:30
 */
interface NeedToScrollUp {

    fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain): Boolean


    abstract class AbstractCount(private val mapper: BirthdayListCountMapper) : NeedToScrollUp {
        protected abstract fun handle(before: Int, after: Int): Boolean

        override fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain) =
            handle(before.map(mapper), after.map(mapper))
    }

    class ListsNotMatch : NeedToScrollUp {
        override fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain) =
            before.map(BirthdayListDomainCompareMapper.CompareObject(after)).not()
    }

    class ChangedToOneElement : AbstractCount(BirthdayListCountMapper.CountWithoutHeaders()) {
        override fun handle(before: Int, after: Int): Boolean = abs(before - after) != 1
    }

    class Group(private vararg val group: NeedToScrollUp) : NeedToScrollUp {
        override fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain): Boolean {
            return group.all { it.needToScrollUp(before, after) }
        }
    }
}