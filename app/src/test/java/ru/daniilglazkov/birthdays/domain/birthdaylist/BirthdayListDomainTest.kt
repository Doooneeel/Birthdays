package ru.daniilglazkov.birthdays.domain.birthdaylist

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import java.time.LocalDate

class BirthdayListDomainTest {

    @Test
    fun test_calculate_position() {
        val birthdayListDomain = BirthdayListDomain.Base(
            BirthdayDomain.Base(-3, "o", LocalDate.MIN),
            BirthdayDomain.Base(-2, "o", LocalDate.MIN),
            BirthdayDomain.Header(-10, ""),
            BirthdayDomain.Base(-1, "i", LocalDate.MIN),
            BirthdayDomain.Base(0, "a", LocalDate.MIN),
            BirthdayDomain.Base(1, "b", LocalDate.MIN),
            BirthdayDomain.Header(-20, ""),
            BirthdayDomain.Base(2, "c", LocalDate.MIN),
            BirthdayDomain.Base(3, "d", LocalDate.MIN),
            BirthdayDomain.Base(4, "e", LocalDate.MIN),
            BirthdayDomain.Header(-30, ""),
            BirthdayDomain.Base(5, "f", LocalDate.MIN),
            BirthdayDomain.Base(6, "w", LocalDate.MIN),
            BirthdayDomain.Header(-40, ""),
        )

        assertEquals(5, birthdayListDomain.position(1))
        assertEquals(12, birthdayListDomain.position(id = 6))
        assertEquals(13, birthdayListDomain.position(id = -40))
        assertEquals(0, birthdayListDomain.position(id = -3))
        assertEquals(4, birthdayListDomain.position(id = 0))
        assertEquals(6, birthdayListDomain.position(id = -20))
        assertEquals(1, birthdayListDomain.position(id = -2))
    }

    @Test
    fun test_calculate_position_empty_list() {
        val birthdayListDomain = BirthdayListDomain.Base()

        assertEquals(-1, birthdayListDomain.position(id = 1))
        assertEquals(-1, birthdayListDomain.position(id = -1))
        assertEquals(-1, birthdayListDomain.position(id = 0))
        assertEquals(-1, birthdayListDomain.position(id = 10))
    }
}