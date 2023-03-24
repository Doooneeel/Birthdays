package ru.daniilglazkov.birthdays.domain.zodiac.greek

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.zodiac.BaseGreekZodiacDomainList

/**
 * @author Danil Glazkov on 11.03.2023, 13:22
 */
class GreekZodiacGroupsTest {

    private val list = listOf(
        GreekZodiacDomain.Base(0, "a", 0..10),
        GreekZodiacDomain.Base(1, "b", 11..20),
        GreekZodiacDomain.Base(2, "c", 30..Int.MAX_VALUE),
    )

    private val zodiacGroups = GreekZodiacGroups.Base(object : GreekZodiacDomainList {
        override fun greekZodiacs() = list
    })

    @Test
    fun test() {
        assertEquals(list[0], zodiacGroups.group(0))
        assertEquals(list[0], zodiacGroups.group(1))
        assertEquals(list[0], zodiacGroups.group(5))
        assertEquals(list[0], zodiacGroups.group(9))
        assertEquals(list[0], zodiacGroups.group(10))

        assertEquals(list[1], zodiacGroups.group(11))
        assertEquals(list[1], zodiacGroups.group(15))
        assertEquals(list[1], zodiacGroups.group(19))
        assertEquals(list[1], zodiacGroups.group(20))

        assertEquals(list[2], zodiacGroups.group(30))
        assertEquals(list[2], zodiacGroups.group(31))
        assertEquals(list[2], zodiacGroups.group(10000))
        assertEquals(list[2], zodiacGroups.group(Int.MAX_VALUE))
    }

    @Test
    fun test_out_of_range() {
        assertThrows(NotFoundException::class.java) { zodiacGroups.group(21) }
        assertThrows(NotFoundException::class.java) { zodiacGroups.group(29) }
    }

    @Test
    fun test_base_groups() {
        val baseGroups = GreekZodiacGroups.Base(
            BaseGreekZodiacDomainList(BaseUiTest.TestManageResources())
        )

        for(day in 1..366) { baseGroups.group(day) }

        for(day in -100..0) {
            assertThrows(NotFoundException::class.java) { baseGroups.group(day) }
        }

        for(day in 367..500) {
            assertThrows(NotFoundException::class.java) { baseGroups.group(day) }
        }
    }
}