package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 30.08.2022, 22:23
 */
interface NeedToScrollUpBirthdayList {
    fun needToScroll(changedList: BirthdayListDomain): Boolean

    class Base(private val validate: NeedToScrollUp) : NeedToScrollUpBirthdayList {
        private var previousList: BirthdayListDomain = BirthdayListDomain.Empty

        override fun needToScroll(changedList: BirthdayListDomain): Boolean {
            return validate.needToScrollUp(previousList, changedList).also {
                previousList = changedList
            }
        }
    }

}