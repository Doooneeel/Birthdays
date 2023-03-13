package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Danil Glazkov on 30.01.2023, 23:42
 */
class AgeGroupsTest {
    private val ageGroups = AgeGroups.Base()

    @Test
    fun test_group() {
        assertEquals(AgeRange.Child, ageGroups.group(Int.MIN_VALUE))
        assertEquals(AgeRange.Child, ageGroups.group(-1))
        assertEquals(AgeRange.Child, ageGroups.group(-10))
        assertEquals(AgeRange.Child, ageGroups.group(-25))
        assertEquals(AgeRange.Child, ageGroups.group(-1000))

        assertEquals(AgeRange.Child, ageGroups.group(0))
        assertEquals(AgeRange.Child, ageGroups.group(5))
        assertEquals(AgeRange.Child, ageGroups.group(12))

        assertEquals(AgeRange.Teenage, ageGroups.group(13))
        assertEquals(AgeRange.Teenage, ageGroups.group(16))
        assertEquals(AgeRange.Teenage, ageGroups.group(18))

        assertEquals(AgeRange.Young, ageGroups.group(19))
        assertEquals(AgeRange.Young, ageGroups.group(25))
        assertEquals(AgeRange.Young, ageGroups.group(35))

        assertEquals(AgeRange.Adult, ageGroups.group(36))
        assertEquals(AgeRange.Adult, ageGroups.group(45))
        assertEquals(AgeRange.Adult, ageGroups.group(59))

        assertEquals(AgeRange.Senior, ageGroups.group(60))
        assertEquals(AgeRange.Senior, ageGroups.group(100))
        assertEquals(AgeRange.Senior, ageGroups.group(Int.MAX_VALUE))
    }
}