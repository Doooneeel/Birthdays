package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split.SplitBirthdayList

/**
 * @author Danil Glazkov on 31.01.2023, 1:08
 */
class GroupBirthdayListTest {

    private val emptyBirthdayList = BirthdayListDomain.Base()

    private lateinit var makeHeader: TestMakeHeader
    private lateinit var splitBirthdayList: TestSplitBirthdayList

    private lateinit var groupBirthdayList: GroupBirthdayList


    @Before
    fun setUp() {
        makeHeader = TestMakeHeader()
        splitBirthdayList = TestSplitBirthdayList()
        groupBirthdayList = GroupBirthdayList.Base(splitBirthdayList, makeHeader)
    }


    @Test
    fun test_group() {
        val firstGroup = listOf(
            BirthdayDomain.Mock("a"),
            BirthdayDomain.Mock("b"),
            BirthdayDomain.Mock("c"),
        )
        val secondGroup = listOf(
            BirthdayDomain.Mock("t"),
            BirthdayDomain.Mock("y"),
            BirthdayDomain.Mock("g"),
            BirthdayDomain.Mock("h"),
        )
        val thirdGroup = listOf(BirthdayDomain.Mock("x"), BirthdayDomain.Mock("z"))

        val birthdayGroupList = listOf<BirthdayListDomain>(
            BirthdayListDomain.Base(firstGroup),
            BirthdayListDomain.Base(secondGroup),
            BirthdayListDomain.Base(thirdGroup),
            BirthdayListDomain.Base(secondGroup),
            BirthdayListDomain.Base(firstGroup),
        )
        val header = BirthdayDomain.Header(-1, "header")

        makeHeader.result = header
        splitBirthdayList.result = birthdayGroupList

        val exception = BirthdayListDomain.Base(buildList {
            add(header)
            addAll(firstGroup)
            add(header)
            addAll(secondGroup)
            add(header)
            addAll(thirdGroup)
            add(header)
            addAll(secondGroup)
            add(header)
            addAll(firstGroup)
        })

        val actual: BirthdayListDomain = groupBirthdayList.group(emptyBirthdayList)

        assertEquals(exception, actual)

        assertEquals(1, splitBirthdayList.calledList.size)
        assertEquals(emptyBirthdayList, splitBirthdayList.calledList[0])

        assertEquals(birthdayGroupList.size, makeHeader.calledList.size)

        assertEquals(BirthdayListDomain.Base(firstGroup), makeHeader.calledList[0])
        assertEquals(BirthdayListDomain.Base(secondGroup), makeHeader.calledList[1])
        assertEquals(BirthdayListDomain.Base(thirdGroup), makeHeader.calledList[2])
        assertEquals(BirthdayListDomain.Base(secondGroup), makeHeader.calledList[3])
        assertEquals(BirthdayListDomain.Base(firstGroup), makeHeader.calledList[4])
    }

    @Test
    fun test_group_empty_list() {
        splitBirthdayList.result = emptyList()

        assertEquals(emptyBirthdayList, groupBirthdayList.group(emptyBirthdayList))

        assertEquals(0, makeHeader.calledList.size)
        assertEquals(1, splitBirthdayList.calledList.size)
    }


    private class TestMakeHeader : MakeHeader {

        lateinit var result: BirthdayDomain
        val calledList = mutableListOf<BirthdayListDomain>()
        
        override fun make(group: List<BirthdayDomain>): BirthdayDomain {
            calledList.add(BirthdayListDomain.Base(group))
            return result
        }
    }

    private class TestSplitBirthdayList : SplitBirthdayList {

        lateinit var result: List<BirthdayListDomain>
        val calledList = mutableListOf<BirthdayListDomain>()

        override fun split(data: BirthdayListDomain): List<BirthdayListDomain> {
            calledList.add(data)
            return result
        }
    }
}
