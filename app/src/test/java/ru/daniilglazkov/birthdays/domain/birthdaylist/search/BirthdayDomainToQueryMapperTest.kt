package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.core.text.NormalizeQuery
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.TestCalculateNextEvent
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import java.time.LocalDate
import java.util.*

class BirthdayDomainToQueryMapperTest : TestCore {

    private val english = Locale.ENGLISH
    private val germany = Locale.GERMANY
    private val russian = Locale("ru")

    private val type = BirthdayType.Mock

    @Test
    fun test_name() {
        val list = listOf("a", "b", "c", "d")
        val normalizeQuery = TestNormalizeQuery(list)
        val nameMapper = BirthdayDomainToQueryMapper.Name(normalizeQuery)

        val actual = nameMapper.map(0, "a", LocalDate.MIN, type)

        assertCollectionEquals(list, actual)
        assertEquals(1, normalizeQuery.normalizeCalledList.size)
        assertCollectionEquals(listOf("a"), normalizeQuery.normalizeCalledList)
    }

    @Test
    fun test_name_empty_list() {
        val normalizeQuery = TestNormalizeQuery(emptyList())
        val nameMapper = BirthdayDomainToQueryMapper.Name(normalizeQuery)

        val actual = nameMapper.map(0, "a", LocalDate.MIN, type)

        assertCollectionEquals(emptyList<String>(), actual)
        assertEquals(1, normalizeQuery.normalizeCalledList.size)
        assertCollectionEquals(listOf("a"), normalizeQuery.normalizeCalledList)
    }

    @Test
    fun test_with_next_event() {
        val date = LocalDate.of(2002, 1, 23)

        val testQueryMapper = TestBirthdayDomainToQueryMapper()
        val nextEvent = TestCalculateNextEvent(date)

        val queryMapper = BirthdayDomainToQueryMapper.WithNextEvent(nextEvent, testQueryMapper)

        testQueryMapper.result.add("result")

        val actual: List<String> = queryMapper.map(-1, "a", LocalDate.MAX, type)

        assertEquals(testQueryMapper.result, actual)

        assertEquals(1, nextEvent.calledList.size)
        assertEquals(LocalDate.MAX, nextEvent.calledList[0])
    }

    @Test
    fun test_date() {
        val englishDate = BirthdayDomainToQueryMapper.Date(
            DateTextFormat.Mock("month"),
            DateTextFormat.Mock("day of week"),
            english
        )
        val germanyDate = BirthdayDomainToQueryMapper.Date(
            DateTextFormat.Mock("monat"),
            DateTextFormat.Mock("wochentag"),
            germany
        )
        val russianDate = BirthdayDomainToQueryMapper.Date(
            DateTextFormat.Mock("месяц"),
            DateTextFormat.Mock("день"),
            russian
        )

        assertCollectionEquals(
            listOf("15", "month", "2002", "day of week"),
            englishDate.map(0, "", LocalDate.of(2002, 1, 15), type)
        )
        assertCollectionEquals(
            listOf("20", "month", "2020", "day of week"),
            englishDate.map(0, "",  LocalDate.of(2020, 1, 20), type)
        )
        assertCollectionEquals(
            listOf("10", "month", "2030", "day of week"),
            englishDate.map(0, "", LocalDate.of(2030, 1, 10), type)
        )


        assertCollectionEquals(
            listOf( "15", "monat", "2015", "wochentag"),
            germanyDate.map(0, "", LocalDate.of(2015, 1, 15), type)
        )
        assertCollectionEquals(
            listOf("11", "monat", "2019", "wochentag"),
            germanyDate.map(0, "", LocalDate.of(2019, 1, 11), type)
        )
        assertCollectionEquals(
            listOf("29", "monat", "2020", "wochentag"),
            germanyDate.map(0, "", LocalDate.of(2020, 1, 29), type)
        )


        assertCollectionEquals(
            listOf("20", "месяц", "2012", "день"),
            russianDate.map(0, "", LocalDate.of(2012, 1, 20), type)
        )
        assertCollectionEquals(
            listOf("2", "месяц", "3202", "день"),
            russianDate.map(0, "", LocalDate.of(3202, 1, 2), type)
        )
        assertCollectionEquals(
            listOf("12", "месяц", "2412", "день"),
            russianDate.map(0, "", LocalDate.of(2412, 1, 12), type)
        )
    }

    @Test
    fun test_zodiac() {
        val date = LocalDate.of(2033, 12, 3)
        val name = "name"

        val birthdayToZodiacMapper = TestZodiacDomainToQueryMapper()
        val zodiacToQueryMapper = ZodiacToQueryMapper()

        birthdayToZodiacMapper.result = ChineseZodiacDomain.Base(100, "zodiac")

        val zodiacMapper = BirthdayDomainToQueryMapper.ZodiacMapper(
            birthdayToZodiacMapper,
            zodiacToQueryMapper
        )

        assertCollectionEquals(
            listOf("100 zodiac"),
            zodiacMapper.map(0, name, date, BirthdayType.Base)
        )

        assertEquals(1, zodiacToQueryMapper.calledList.size)
        assertEquals(Pair(100, "zodiac"), zodiacToQueryMapper.calledList[0])

        assertEquals(1, birthdayToZodiacMapper.calledList.size)
        assertEquals(BirthdayDomain.Base(0, name, date), birthdayToZodiacMapper.calledList[0])
    }


    private class ZodiacToQueryMapper : ZodiacDomain.Mapper<String> {
        val calledList = mutableListOf<Pair<Int, String>>()

        override fun map(ordinal: Int, name: String): String {
            calledList.add(Pair(ordinal, name))
            return "$ordinal $name"
        }
    }

    private class TestZodiacDomainToQueryMapper : BirthdayDomain.Mapper<ZodiacDomain> {

        lateinit var result: ZodiacDomain
        val calledList = mutableListOf<BirthdayDomain>()

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): ZodiacDomain {
            calledList.add(BirthdayDomain.Base(id, name, date))
            return result
        }
    }

    private class TestBirthdayDomainToQueryMapper : BirthdayDomainToQueryMapper {

        val calledList = mutableListOf<BirthdayDomain>()
        val result = mutableListOf<String>()

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): List<String> {
            calledList.add(BirthdayDomain.Base(id, name, date))
            return result
        }
    }

    private class TestNormalizeQuery(var result: List<String> = emptyList()) : NormalizeQuery {

        val normalizeCalledList = mutableListOf<CharSequence>()

        override fun normalize(query: CharSequence): List<String> {
            normalizeCalledList.add(query)
            return result
        }
    }
}