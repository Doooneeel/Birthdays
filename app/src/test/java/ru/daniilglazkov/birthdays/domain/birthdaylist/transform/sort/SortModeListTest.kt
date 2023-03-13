package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Danil Glazkov on 03.03.2023, 12:26
 */
class SortModeListTest {

    private val sortModeList = SortModeList.Base()

    @Test
    fun test_fetch_by_id() {
        assertEquals(SortMode.NAME, sortModeList.fetchById(SortMode.NAME.id()))
        assertEquals(SortMode.DATE, sortModeList.fetchById(SortMode.DATE.id()))
        assertEquals(SortMode.AGE, sortModeList.fetchById(SortMode.AGE.id()))
        assertEquals(SortMode.ZODIAC, sortModeList.fetchById(SortMode.ZODIAC.id()))
        assertEquals(SortMode.MONTH, sortModeList.fetchById(SortMode.MONTH.id()))
        assertEquals(SortMode.YEAR, sortModeList.fetchById(SortMode.YEAR.id()))
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_fetch_by_id_unknown_id() { sortModeList.fetchById(-1) }
}