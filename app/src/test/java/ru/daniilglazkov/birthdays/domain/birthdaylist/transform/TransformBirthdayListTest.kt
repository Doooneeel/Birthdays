package ru.daniilglazkov.birthdays.domain.birthdaylist.transform

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortBirthdayList

/**
 * @author Danil Glazkov on 03.03.2023, 14:48
 */
class TransformBirthdayListTest {

    @Test
    fun test_transform() {
        val birthday = BirthdayListDomain.Base(BirthdayDomain.Mock("a"))

        val sort = TestSortBirthdayList()
        val group = TestGroupBirthdayList()

        sort.result = BirthdayListDomain.Base(BirthdayDomain.Mock("a"), BirthdayDomain.Mock("b"))
        group.result = BirthdayListDomain.Base(BirthdayDomain.Mock("c"), BirthdayDomain.Mock("x"))

        val transformBirthdayList = TransformBirthdayList.Base(sort, group)

        assertEquals(group.result, transformBirthdayList.transform(birthday))

        assertEquals(1, sort.calledList.size)
        assertEquals(birthday, sort.calledList[0])

        assertEquals(1, group.calledList.size)
        assertEquals(sort.result, group.calledList[0])
    }


    private class TestGroupBirthdayList : GroupBirthdayList {

        val calledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: BirthdayListDomain

        override fun group(source: BirthdayListDomain): BirthdayListDomain {
            calledList.add(source)
            return result
        }
    }

    private class TestSortBirthdayList : SortBirthdayList {

        val calledList = mutableListOf<BirthdayListDomain>()
        lateinit var result: BirthdayListDomain

        override fun sort(data: BirthdayListDomain): BirthdayListDomain {
            calledList.add(data)
            return result
        }
    }
}