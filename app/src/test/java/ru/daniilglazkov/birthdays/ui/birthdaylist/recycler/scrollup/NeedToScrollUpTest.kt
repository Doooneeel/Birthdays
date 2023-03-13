package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.scrollup

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 07.02.2023, 4:08
 */
class NeedToScrollUpTest {

    private val changedToOneElement = NeedToScrollUp.ChangedToOneElement()
    private val emptyList = emptyList<BirthdayDomain>()

    @Test
    fun test_changed_to_one_element() {
        val threeElements = listOf(
            BirthdayDomain.Mock("a"),
            BirthdayDomain.Mock("b"),
            BirthdayDomain.Mock("c"),
        )
        val twoElements = listOf(BirthdayDomain.Mock("a"), BirthdayDomain.Mock("b"))
        val oneElement = listOf(BirthdayDomain.Mock("a"))

        assertNotNeedToScrollUp(changedToOneElement, threeElements, twoElements)
        assertNotNeedToScrollUp(changedToOneElement, twoElements, oneElement)
        assertNeedToScrollUp(changedToOneElement, threeElements, oneElement)
        assertNeedToScrollUp(changedToOneElement, threeElements, emptyList)
        assertNeedToScrollUp(changedToOneElement, twoElements, emptyList)
        assertNotNeedToScrollUp(changedToOneElement, oneElement, emptyList)
    }

    @Test
    fun test_headers_changed_to_one_element() {
        val threeElements = listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Header(-2, "b"),
            BirthdayDomain.Header(-3, "c"),
        )
        val twoElements = listOf(BirthdayDomain.Header(-4, "a"), BirthdayDomain.Header(-5, "b"))
        val oneElement = listOf(BirthdayDomain.Header(-7, "a"))

        assertNeedToScrollUp(changedToOneElement, threeElements, emptyList)
        assertNeedToScrollUp(changedToOneElement, emptyList, oneElement)
        assertNeedToScrollUp(changedToOneElement, oneElement, threeElements)
        assertNeedToScrollUp(changedToOneElement, threeElements, twoElements)
        assertNeedToScrollUp(changedToOneElement, twoElements, oneElement)

        assertNeedToScrollUp(changedToOneElement, oneElement, oneElement)
        assertNeedToScrollUp(changedToOneElement, threeElements, threeElements)
        assertNeedToScrollUp(changedToOneElement, emptyList, emptyList)
    }

    @Test
    fun test_lists_not_matches() {
        val listsNotMatch = NeedToScrollUp.ListsNotMatch()

        val list1 = listOf(
            BirthdayDomain.Mock("a"),
            BirthdayDomain.Mock("b"),
            BirthdayDomain.Header(-1, "b"),
        )
        val list2 = listOf(
            BirthdayDomain.Mock("b"),
            BirthdayDomain.Header(-1, "b"),
        )
        val list3 = listOf(
            BirthdayDomain.Mock("a"),
            BirthdayDomain.Mock("b"),
            BirthdayDomain.Header(-1, "b"),
            BirthdayDomain.Mock("NEW"),
        )
        val twoBase = listOf(
            BirthdayDomain.Mock("a"),
            BirthdayDomain.Mock("b"),
        )
        val twoHeaders = listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Header(-2, "b")
        )

        assertNeedToScrollUp(listsNotMatch, list1, emptyList)
        assertNeedToScrollUp(listsNotMatch, list1, twoBase)
        assertNeedToScrollUp(listsNotMatch, list1, twoHeaders)
        assertNeedToScrollUp(listsNotMatch, list1, list2)
        assertNeedToScrollUp(listsNotMatch, list1, list3)

        assertNeedToScrollUp(listsNotMatch, list2, emptyList)
        assertNeedToScrollUp(listsNotMatch, list2, list3)
        assertNeedToScrollUp(listsNotMatch, list2, twoHeaders)

        assertNeedToScrollUp(listsNotMatch, twoBase, emptyList)
        assertNeedToScrollUp(listsNotMatch, twoBase, list1)
        assertNeedToScrollUp(listsNotMatch, twoBase, list2)
        assertNeedToScrollUp(listsNotMatch, twoBase, list3)

        assertNotNeedToScrollUp(listsNotMatch, list1, list1)
        assertNotNeedToScrollUp(listsNotMatch, emptyList, emptyList)
        assertNeedToScrollUp(listsNotMatch, emptyList, twoHeaders)

        assertNeedToScrollUp(listsNotMatch, twoBase, twoHeaders)
    }

    private fun assertNeedToScrollUp(
        needToScrollUp: NeedToScrollUp,
        beforeList: List<BirthdayDomain>,
        afterList: List<BirthdayDomain>
    ) {
        val before = BirthdayListDomain.Base(beforeList)
        val after = BirthdayListDomain.Base(afterList)

        assertTrue(needToScrollUp.needToScrollUp(before, after))
    }

    private fun assertNotNeedToScrollUp(
        needToScrollUp: NeedToScrollUp,
        beforeList: List<BirthdayDomain>,
        afterList: List<BirthdayDomain>
    ) {
        val before = BirthdayListDomain.Base(beforeList)
        val after = BirthdayListDomain.Base(afterList)

        assertFalse(needToScrollUp.needToScrollUp(before, after))
    }
}