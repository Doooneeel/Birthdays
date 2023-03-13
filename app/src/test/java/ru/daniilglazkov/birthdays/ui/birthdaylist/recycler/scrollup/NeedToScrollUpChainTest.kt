package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 07.02.2023, 5:44
 */
class NeedToScrollUpChainTest {

    private lateinit var base: TestNeedToScrollUp
    private lateinit var next: TestNeedToScrollUp
    private lateinit var chain: NeedToScrollUp

    @Before
    fun setUp() {
        base = TestNeedToScrollUp()
        next = TestNeedToScrollUp()
        chain = NeedToScrollUp.Group(base, next)
    }


    @Test
    fun test_base_true_next_true() {
        base.result = true
        next.result = true

        assertEqualsNeedToScrollUp(true, 1)
    }

    @Test
    fun test_base_true_next_false() {
        base.result = true
        next.result = false

        assertEqualsNeedToScrollUp(false, 1)
    }

    @Test
    fun test_base_false_next_true() {
        base.result = false
        next.result = true

        assertEqualsNeedToScrollUp(false, 0)
    }

    @Test
    fun test_base_false_next_false() {
        base.result = false
        next.result = false

        assertEqualsNeedToScrollUp(false, 0)
    }

    private fun assertEqualsNeedToScrollUp(expected: Boolean, nextCallCount: Int) {
        val birthdays = BirthdayListDomain.Base()
        assertEquals(expected, chain.needToScrollUp(birthdays, birthdays))

        assertEquals(1, base.calledList.size)
        assertEquals(nextCallCount, next.calledList.size)
    }
}