package ru.daniilglazkov.birthdays.domain.birthdaylist

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.03.2023, 20:57
 */
class BirthdayListDomainCompareMapperTest {

    @Test
    fun test_compare_object() {
        val compare = BirthdayListDomainCompareMapper.CompareObject(BirthdayListDomain.Base(
            BirthdayDomain.Header(-1, "header"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        ))

        assertTrue(compare.map(listOf(
            BirthdayDomain.Header(-1, "header"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(BirthdayDomain.Header(-1, "h"))))

        assertFalse(compare.map(listOf()))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "header"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "header"),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-2, "header"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Base(2, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Base(1, "a", LocalDate.MAX),
            BirthdayDomain.Base(2, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Header(2, "a"),
            BirthdayDomain.Header(3, "a"),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Header(-1, "a"),
            BirthdayDomain.Header(-1, "a"),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "header"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(1, "b", LocalDate.MAX),
        )))

        assertFalse(compare.map(listOf(
            BirthdayDomain.Header(-1, "header"),
            BirthdayDomain.Base(1, "a", LocalDate.MIN),
            BirthdayDomain.Base(2, "b", LocalDate.MIN),
        )))
    }

    @Test
    fun test_compare_object_one_element() {
        val compare = BirthdayListDomainCompareMapper.CompareObject(BirthdayListDomain.Base(
            BirthdayDomain.Base(0, "a", LocalDate.MIN)
        ))

        assertTrue(compare.map(listOf(BirthdayDomain.Base(0, "a", LocalDate.MIN))))

        assertFalse(compare.map(listOf(BirthdayDomain.Base(1, "a", LocalDate.MIN))))
        assertFalse(compare.map(listOf(BirthdayDomain.Base(0, "b", LocalDate.MIN))))
        assertFalse(compare.map(listOf(BirthdayDomain.Base(0, "a", LocalDate.MAX))))

        assertFalse(compare.map(listOf(BirthdayDomain.Base(1, "b", LocalDate.MAX))))
    }

    @Test
    fun test_compare_object_empty_list() {
        val compare = BirthdayListDomainCompareMapper.CompareObject(
            BirthdayListDomain.Base()
        )

        assertTrue(compare.map(emptyList()))

        assertFalse(compare.map(listOf(BirthdayDomain.Base(0, "a", LocalDate.MAX))))
        assertFalse(compare.map(listOf(BirthdayDomain.Base(0, "", LocalDate.MIN))))
    }
}