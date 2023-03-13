package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 07.02.2023, 5:56
 */
class TestNeedToScrollUp : NeedToScrollUp {

    val calledList = mutableListOf<Pair<BirthdayListDomain, BirthdayListDomain>>()
    var result: Boolean = false

    override fun needToScrollUp(before: BirthdayListDomain, after: BirthdayListDomain): Boolean {
        calledList.add(Pair(before, after))
        return result
    }
}
