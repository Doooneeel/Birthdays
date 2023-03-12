package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort

/**
 * @author Danil Glazkov on 03.03.2023, 11:39
 */
interface SortModeList {

    fun fetchById(id: Int): SortMode


    class Base : SortModeList {
        private val sortModeList = listOf(
            SortMode.DATE, SortMode.AGE, SortMode.NAME,
            SortMode.YEAR, SortMode.MONTH, SortMode.ZODIAC
        )

        override fun fetchById(id: Int): SortMode = sortModeList.find { it.compareId(id) }
            ?: throw IllegalArgumentException("Unknown sort mode, id: $id")
    }

}