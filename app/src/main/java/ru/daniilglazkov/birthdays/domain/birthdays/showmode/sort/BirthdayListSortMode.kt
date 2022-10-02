package ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort

import ru.daniilglazkov.birthdays.core.Matches

/**
 * @author Danil Glazkov on 21.09.2022, 05:26
 */
abstract class BirthdayListSortMode(private val tag: String) : Matches<BirthdayListSortMode> {
    override fun matches(data: BirthdayListSortMode): Boolean = data.tag == this.tag

    class Age : BirthdayListSortMode(tag = "${TAG}Age")
    class Name : BirthdayListSortMode(tag = "${TAG}Name")
    class Month : BirthdayListSortMode(tag = "${TAG}Month")
    class Date : BirthdayListSortMode(tag = "${TAG}Date")
    class Year : BirthdayListSortMode(tag = "${TAG}Year")

    companion object {
        private const val TAG = "BirthdayListSortModeBy"
    }
}