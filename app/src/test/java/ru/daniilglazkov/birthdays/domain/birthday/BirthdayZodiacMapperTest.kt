package ru.daniilglazkov.birthdays.domain.birthday

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomainList
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate

class BirthdayZodiacMapperTest {

    private lateinit var chineseZodiacList: TestChineseZodiacDomainList
    private lateinit var greekZodiacGroups: TestGreekZodiacGroups

    private lateinit var chinese: BirthdayZodiacMapper
    private lateinit var greek: BirthdayZodiacMapper

    @Before
    fun setUp() {
        greekZodiacGroups = TestGreekZodiacGroups()
        chineseZodiacList = TestChineseZodiacDomainList()

        greek = BirthdayZodiacMapper.Greek(greekZodiacGroups)
        chinese = BirthdayZodiacMapper.Chinese(chineseZodiacList)
    }

    @Test
    fun test_map() {
        chineseZodiacList.zodiacs = listOf(
            ChineseZodiacDomain.Base(0, "a"),
            ChineseZodiacDomain.Base(1, "b"),
            ChineseZodiacDomain.Base(2, "c"),
            ChineseZodiacDomain.Base(3, "d"),
            ChineseZodiacDomain.Base(4, "e"),
            ChineseZodiacDomain.Base(5, "f"),
            ChineseZodiacDomain.Base(6, "g"),
            ChineseZodiacDomain.Base(11, "w")
        )
        assertEquals(0, chineseZodiacList.callCount)

        val zodiac1 = chinese.map(0, "", LocalDate.of(2020, 1, 1), BirthdayType.Base)
        assertEquals(ChineseZodiacDomain.Base(4, "e"), zodiac1) //2020 % 12 == 4

        val zodiac2 = chinese.map(0, "", LocalDate.of(2015, 1, 1), BirthdayType.Base)
        assertEquals(ChineseZodiacDomain.Base(11, "w"), zodiac2) //2015 % 12 == 11

        val zodiac3 = chinese.map(0, "", LocalDate.of(1980, 1, 1), BirthdayType.Base)
        assertEquals(ChineseZodiacDomain.Base(0, "a"), zodiac3) //1980 % 12 == 0

        assertEquals(1, chineseZodiacList.callCount)
    }

    @Test
    fun test_map_zodiac_doesnt_exist() {
        chineseZodiacList.zodiacs = listOf(
            ChineseZodiacDomain.Base(1, "a"),
            ChineseZodiacDomain.Base(3, "b"),
            ChineseZodiacDomain.Base(4, "c"),
        )
        val date = LocalDate.of(2022, 1, 1) //2020 % 12 == 6

        assertEquals(0, chineseZodiacList.callCount)

        assertThrows(NotFoundException::class.java) {
            chinese.map(0, "", date, BirthdayType.Base)
        }

        assertEquals(1, chineseZodiacList.callCount)
    }

    @Test
    fun test_greek() {
        val date = LocalDate.of(2000, 7, 16)
        greekZodiacGroups.result = GreekZodiacDomain.Leo("")

        assertEquals(greekZodiacGroups.result, greek.map(0, "", date, BirthdayType.Mock))

        assertEquals(1, greekZodiacGroups.calledList.size)
        assertEquals(date.dayOfYear, greekZodiacGroups.calledList[0])
    }


    private class TestGreekZodiacGroups : GreekZodiacGroups {

        lateinit var result: GreekZodiacDomain
        val calledList = mutableListOf<Int>()

        override fun group(value: Int): GreekZodiacDomain {
            calledList.add(value)
            return result
        }
    }

    private class TestChineseZodiacDomainList : ChineseZodiacDomainList {
        lateinit var zodiacs: List<ChineseZodiacDomain>
        var callCount = 0

        override fun chineseZodiacs(): List<ChineseZodiacDomain> {
            ++callCount
            return zodiacs
        }
    }
}

