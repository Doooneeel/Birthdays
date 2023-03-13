package ru.daniilglazkov.birthdays.domain.birthday

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

/**
 * @author Danil Glazkov on 30.01.2023, 19:28
 */
class BirthdayDomainTest {

    @Test
    fun test_compare_id() {
        assertTrue(BirthdayDomain.Base(0, "a", LocalDate.MAX).compareId(0))
        assertTrue(BirthdayDomain.Base(-1, "b", LocalDate.MAX).compareId(-1))
        assertTrue(BirthdayDomain.Base(1, "c", LocalDate.MAX).compareId(1))
        assertTrue(BirthdayDomain.Base(1000, "d", LocalDate.MAX).compareId(1000))

        assertTrue(BirthdayDomain.Header(1000, "a").compareId(1000))
        assertTrue(BirthdayDomain.Header(-1, "b").compareId(-1))
        assertTrue(BirthdayDomain.Header(2, "c").compareId(2))
        assertTrue(BirthdayDomain.Header(0, "d").compareId(0))

        assertFalse(BirthdayDomain.Base(1001, "a", LocalDate.MIN).compareId(0))
        assertFalse(BirthdayDomain.Base(2, "b", LocalDate.MIN).compareId(1))

        assertFalse(BirthdayDomain.Header(1001, "d").compareId(1000))
        assertFalse(BirthdayDomain.Header(1, "d").compareId(0))
    }

}