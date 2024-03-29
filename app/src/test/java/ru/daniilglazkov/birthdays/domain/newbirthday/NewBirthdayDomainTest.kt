package ru.daniilglazkov.birthdays.domain.newbirthday

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import java.time.LocalDate

class NewBirthdayDomainTest {

    @Test
    fun test_create() {
        val firstBirthday = NewBirthdayDomain.Base("a", LocalDate.MAX)
        assertEquals(BirthdayDomain.Base(10, "a", LocalDate.MAX), firstBirthday.create(10))

        val secondBirthday = NewBirthdayDomain.Base("n", LocalDate.MIN)
        assertEquals(BirthdayDomain.Base(-1, "n", LocalDate.MIN), secondBirthday.create(-1))

        val thirdBirthday = NewBirthdayDomain.Base("X", LocalDate.MAX)
        assertEquals(BirthdayDomain.Base(0, "X", LocalDate.MAX), thirdBirthday.create(0))
    }
}