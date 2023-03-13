package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.domain.core.text.AddDelimiter
import java.time.LocalDate

/**
 * @author Danil Glazkov on 06.02.2023, 10:53
 */
class BirthdayListDomainToChipsMapperTest : BaseUiTest() {

    private lateinit var addDelimiter: TestAddDelimiter
    private lateinit var manageResources: TestManageResources
    private lateinit var birthdayDomainToChipMapper: TestBirthdayDomainToChipMapper
    private lateinit var birthdayCheckMapper: TestCheckMapper

    private lateinit var mapper: BirthdayListDomainToChipsMapper


    @Before
    fun setUp() {
        addDelimiter = TestAddDelimiter()
        manageResources = TestManageResources()
        birthdayDomainToChipMapper = TestBirthdayDomainToChipMapper()
        birthdayCheckMapper = TestCheckMapper()

        mapper = BirthdayListDomainToChipsMapper.Base(
            addDelimiter,
            birthdayCheckMapper,
            birthdayDomainToChipMapper,
            manageResources
        )
    }

    @Test
    fun test_empty_list() {
        assertEquals(ChipListUi.Empty, mapper.map(emptyList()))

        assertEquals(0, manageResources.stringCount)
        assertEquals(0, addDelimiter.calledList.size)
        assertEquals(0, birthdayCheckMapper.callCount)
        assertEquals(0, birthdayDomainToChipMapper.callCount)
    }

    @Test
    fun test_without_headers() {
        manageResources.string = "total"
        addDelimiter.result = "total: 123"

        val list = listOf<BirthdayDomain>(
            BirthdayDomain.Base(0, "a", LocalDate.MIN),
            BirthdayDomain.Base(1, "b", LocalDate.MAX),
            BirthdayDomain.Base(2, "c", LocalDate.MAX),
            BirthdayDomain.Base(3, "d", LocalDate.MIN),
            BirthdayDomain.Base(4, "e", LocalDate.MAX),
            BirthdayDomain.Base(5, "f", LocalDate.MIN),
            BirthdayDomain.Base(6, "w", LocalDate.MAX),
        )
        val expected: ChipListUi = ChipListUi.Base(ChipUi.WithoutId("total: 123"))
        val actual: ChipListUi = mapper.map(list)

        assertEquals(expected, actual)

        assertEquals(1, manageResources.stringCount)
        assertEquals(list.size, birthdayCheckMapper.callCount)
        assertEquals(1, addDelimiter.calledList.size)
        assertEquals(Pair(manageResources.string, list.size.toString()), addDelimiter.calledList[0])
        assertEquals(0, birthdayDomainToChipMapper.callCount)
    }

    @Test
    fun test_one_element() {
        manageResources.string = "tot"
        addDelimiter.result = "total: 1"

        val base = listOf<BirthdayDomain>(BirthdayDomain.Base(123, "base", LocalDate.MIN))
        val header = listOf<BirthdayDomain>(BirthdayDomain.Header(10, "header"))

        assertEquals(ChipListUi.Base(ChipUi.WithoutId("total: 1")), mapper.map(base))
        assertEquals(
            ChipListUi.Base(
                ChipUi.WithoutId("total: 1"),
                ChipUi.Base(10, "header"),
            ),
            mapper.map(header)
        )

        assertEquals(1, manageResources.stringCount)
        assertEquals(2, birthdayCheckMapper.callCount)
        assertEquals(2, addDelimiter.calledList.size)
        assertEquals(Pair(manageResources.string, "1"), addDelimiter.calledList[0])
        assertEquals(Pair(manageResources.string, "0"), addDelimiter.calledList[1])

        assertEquals(1, birthdayDomainToChipMapper.callCount)
    }

    @Test
    fun test_base_and_headers() {
        addDelimiter.result = "first"
        manageResources.string = "str"

        val countWithoutHeaders = 6

        val list = listOf<BirthdayDomain>(
            BirthdayDomain.Header(-1, "header_1"),
            BirthdayDomain.Header(-2, "header_2"),
            BirthdayDomain.Base(1, "a", LocalDate.MAX),
            BirthdayDomain.Base(2, "b", LocalDate.MIN),
            BirthdayDomain.Header(-3, "header_3"),
            BirthdayDomain.Base(3, "c", LocalDate.MIN),
            BirthdayDomain.Base(4, "d", LocalDate.MIN),
            BirthdayDomain.Base(5, "e", LocalDate.MIN),
            BirthdayDomain.Header(-4, "header_4"),
            BirthdayDomain.Base(6, "f", LocalDate.MIN),
            BirthdayDomain.Header(-5, "header_5")
        )
        val expected: ChipListUi = ChipListUi.Base(listOf<ChipUi>(
            ChipUi.WithoutId(addDelimiter.result),
            ChipUi.Base(-1, "header_1"),
            ChipUi.Base(-2, "header_2"),
            ChipUi.Base(-3, "header_3"),
            ChipUi.Base(-4, "header_4"),
            ChipUi.Base(-5, "header_5")
        ))
        val actual: ChipListUi = mapper.map(list)

        assertEquals(expected, actual)

        assertEquals(1, manageResources.stringCount)
        assertEquals(list.size, birthdayCheckMapper.callCount)
        assertEquals(5, birthdayDomainToChipMapper.callCount)
        assertEquals(1, addDelimiter.calledList.size)
        assertEquals(
            Pair(manageResources.string, "$countWithoutHeaders"),
            addDelimiter.calledList[0]
        )

        mapper.map(list)
        assertEquals(1, manageResources.stringCount)
    }

    @Test
    fun test_only_headers() {
        addDelimiter.result = "first"
        manageResources.string = "title"

        val countWithoutHeaders = 0

        val list = listOf<BirthdayDomain>(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Header(-2, "b"),
            BirthdayDomain.Header(-3, "c"),
            BirthdayDomain.Header(-4, "d"),
            BirthdayDomain.Header(-5, "e"),
        )
        val expected: ChipListUi = ChipListUi.Base(listOf(
            ChipUi.WithoutId(addDelimiter.result),
            ChipUi.Base(-1, "a"),
            ChipUi.Base(-2, "b"),
            ChipUi.Base(-3, "c"),
            ChipUi.Base(-4, "d"),
            ChipUi.Base(-5, "e"),
        ))
        val actual: ChipListUi = mapper.map(list)

        assertEquals(expected, actual)

        assertEquals(1, manageResources.stringCount)
        assertEquals(list.size, birthdayCheckMapper.callCount)
        assertEquals(5, birthdayDomainToChipMapper.callCount)
        assertEquals(1, addDelimiter.calledList.size)
        assertEquals(
            Pair(manageResources.string, "$countWithoutHeaders"),
            addDelimiter.calledList[0]
        )

        mapper.map(list)
        assertEquals(1, manageResources.stringCount)
    }


    private class TestCheckMapper : BirthdayCheckMapper {
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Boolean {
            ++callCount
            return type.matches(BirthdayType.Header)
        }
    }

    private class TestBirthdayDomainToChipMapper : BirthdayDomainToChipMapper {
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): ChipUi {
            ++callCount
            return ChipUi.Base(id, name)
        }
    }

    private class TestAddDelimiter : AddDelimiter {

        lateinit var result: String
        val calledList = mutableListOf<Pair<String, String>>()

        override fun add(first: String, second: String): String {
            calledList.add(Pair(first, second))
            return result
        }
    }
}