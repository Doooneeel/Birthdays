package ru.daniilglazkov.birthdays.domain.settings

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 27.02.2023, 6:58
 */
class ChangeSettingsTest {

    @Test
    fun test_change() = runBlocking {
        val changeSettings = ChangeSettings.Base()

        val firstSettings = SettingsDomain.Base(SortMode.AGE, reverse = false, group = true)

        val firstActual = changeSettings.change { previousSettings: SettingsDomain ->
            assertEquals(SettingsDomain.Default, previousSettings)
            firstSettings
        }

        assertEquals(firstSettings, firstActual)

        val secondSettings: SettingsDomain = SettingsDomain.Base(
            SortMode.ZODIAC,
            reverse = true,
            group = true
        )

        val secondActual: SettingsDomain = changeSettings.change { previous: SettingsDomain ->
            assertEquals(firstSettings, previous)
            secondSettings
        }

        assertEquals(secondSettings, secondActual)
    }

}