package ru.daniilglazkov.birthdays.domain.settings

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 01.02.2023, 0:45
 */
class SettingsDomainTest {

    @Test
    fun test_change_sort_mode() {
        val firstSettings = SettingsDomain.Base(SortMode.AGE, reverse = false, group = false)

        val firstExpected = SettingsDomain.Base(SortMode.DATE, reverse = false, group = false)
        val firstActual = firstSettings.changeSortMode(SortMode.DATE)

        assertEquals(firstExpected, firstActual)

        val secondSettings = SettingsDomain.Base(SortMode.YEAR, reverse = true, group = false)

        val secondExpected = SettingsDomain.Base(SortMode.ZODIAC, reverse = true, group = false)
        val secondActual = secondSettings.changeSortMode(SortMode.ZODIAC)

        assertEquals(secondExpected, secondActual)
    }

    @Test
    fun test_change_settings() {
        val firstSettings = SettingsDomain.Base(SortMode.YEAR, reverse = false, group = false)

        val firstExpected = SettingsDomain.Base(SortMode.YEAR, reverse = false, group = true)
        val firstActual = firstSettings.change(reverse = false, group = true)

        assertEquals(firstExpected, firstActual)

        val secondSettings = SettingsDomain.Base(SortMode.ZODIAC, reverse = false, group = false)

        val secondExpected = SettingsDomain.Base(SortMode.ZODIAC, reverse = true, group = true)
        val secondActual = secondSettings.change(reverse = true, group = true)

        assertEquals(secondExpected, secondActual)
    }

}