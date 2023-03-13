package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup.TestNeedToScrollUp
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode

/**
 * @author Danil Glazkov on 07.02.2023, 5:55
 */
class BirthdayListRecyclerStateTest {

    private lateinit var needToScrollUp: TestNeedToScrollUp
    private lateinit var recyclerState: BirthdayListRecyclerState

    private val emptyBirthdayList = BirthdayListDomain.Base()

    @Before
    fun setUp() {
        needToScrollUp = TestNeedToScrollUp()
        recyclerState = BirthdayListRecyclerState.Base(needToScrollUp)
    }


    @Test
    fun test_new_state() {
        needToScrollUp.result = true

        val listNotEmpty = true
        val birthdayList1 = BirthdayListDomain.Base(listOf(BirthdayDomain.Mock("a")))

        val birthdayList2 = BirthdayListDomain.Base(listOf(
            BirthdayDomain.Mock("x"),
            BirthdayDomain.Mock("y"),
        ))
        val birthdayList3 = BirthdayListDomain.Base(listOf(BirthdayDomain.Mock("4")))

        val expected1: RecyclerState = RecyclerState.Base(ScrollMode.SharpUp, listNotEmpty)
        val actual1: RecyclerState = recyclerState.newState(birthdayList1)

        needToScrollUp.result = false

        val expected2: RecyclerState = RecyclerState.Base(ScrollMode.NoScroll, listNotEmpty)
        val actual2: RecyclerState = recyclerState.newState(birthdayList2)

        val expected3: RecyclerState = RecyclerState.Base(ScrollMode.NoScroll, !listNotEmpty)
        val actual3: RecyclerState = recyclerState.newState(emptyBirthdayList)

        needToScrollUp.result = true

        val expected4: RecyclerState = RecyclerState.Base(ScrollMode.SharpUp, listNotEmpty)
        val actual4: RecyclerState = recyclerState.newState(birthdayList3)

        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)
        assertEquals(expected3, actual3)
        assertEquals(expected4, actual4)

        assertEquals(4, needToScrollUp.calledList.size)

        assertEquals(Pair(emptyBirthdayList, birthdayList1), needToScrollUp.calledList[0])

        assertEquals(Pair(birthdayList1, birthdayList2), needToScrollUp.calledList[1])

        assertEquals(Pair(birthdayList2, emptyBirthdayList), needToScrollUp.calledList[2])

        assertEquals(Pair(emptyBirthdayList, birthdayList3), needToScrollUp.calledList[3])
    }
}