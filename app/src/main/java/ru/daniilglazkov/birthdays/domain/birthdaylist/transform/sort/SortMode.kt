package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort

import ru.daniilglazkov.birthdays.domain.core.CompareId

/**
 * @author Danil Glazkov on 02.08.2022, 00:07
 */
abstract class SortMode(private val id: Int) : CompareId {

    override fun compareId(id: Int): Boolean = id == this.id

    fun id(): Int = id


    object DATE : SortMode(id = 0)
    object AGE : SortMode(id = 1)
    object NAME : SortMode(id = 2)
    object YEAR : SortMode(id = 3)
    object MONTH : SortMode(id = 4)
    object ZODIAC : SortMode(id = 5)
    object None : SortMode(id = Int.MIN_VALUE)
}