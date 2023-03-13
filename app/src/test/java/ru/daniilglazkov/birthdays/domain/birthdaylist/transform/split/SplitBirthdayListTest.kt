package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split

import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 04.03.2023, 10:38
 */
class SplitBirthdayListTest : TestCore {

    private val split = SplitBirthdayList.Base(TestSplitPredicate())

    @Test
    fun test_split() {
        val source = BirthdayListDomain.Base(listOf(
            BirthdayDomain.Base(0, "a", LocalDate.MIN),
            BirthdayDomain.Base(1, "b", LocalDate.MIN),
            BirthdayDomain.Base(1, "c", LocalDate.MIN),
            BirthdayDomain.Base(0, "d", LocalDate.MIN),
            BirthdayDomain.Base(2, "e", LocalDate.MIN),
            BirthdayDomain.Base(3, "f", LocalDate.MIN),
            BirthdayDomain.Base(1, "g", LocalDate.MIN),
        ))

        val expected: List<BirthdayListDomain> = listOf(
            BirthdayListDomain.Base(listOf(
                BirthdayDomain.Base(0, "a", LocalDate.MIN),
                BirthdayDomain.Base(0, "d", LocalDate.MIN),
            )),
            BirthdayListDomain.Base(listOf(
                BirthdayDomain.Base(1, "b", LocalDate.MIN),
                BirthdayDomain.Base(1, "c", LocalDate.MIN),
                BirthdayDomain.Base(1, "g", LocalDate.MIN),
            )),
            BirthdayListDomain.Base(listOf(
                BirthdayDomain.Base(2, "e", LocalDate.MIN)
            )),
            BirthdayListDomain.Base(listOf(
                BirthdayDomain.Base(3, "f", LocalDate.MIN)
            ))
        )
        val actual: List<BirthdayListDomain> = split.split(source)

        assertCollectionEquals(expected, actual)
    }

    @Test
    fun test_one_element() {
        val source = BirthdayListDomain.Base(listOf(BirthdayDomain.Mock("a")))

        val expected: List<BirthdayListDomain> = listOf(source)
        val actual: List<BirthdayListDomain> = split.split(source)

        assertCollectionEquals(expected, actual)
    }

    @Test
    fun test_split_empty_list() {
        val expected = emptyList<BirthdayListDomain>()
        val actual = split.split(BirthdayListDomain.Base())

        assertCollectionEquals(expected, actual)
    }


    private class TestSplitPredicate : BirthdayListSplitPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int = id
    }
}