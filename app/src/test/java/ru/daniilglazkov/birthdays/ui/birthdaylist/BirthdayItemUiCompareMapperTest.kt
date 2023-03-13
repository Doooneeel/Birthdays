package ru.daniilglazkov.birthdays.ui.birthdaylist

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Danil Glazkov on 07.02.2023, 9:55
 */
class BirthdayItemUiCompareMapperTest {

    @Test
    fun test_compare_name() {
        val birthday = BirthdayItemUi.Base(0, "a", "", "")
        val compareName = BirthdayItemUiCompareMapper.Name("a")

        assertTrue(birthday.map(compareName))

        val birthday1 = BirthdayItemUi.Base(0, "", "", "")
        val compareName1 = BirthdayItemUiCompareMapper.Name("")

        assertTrue(birthday1.map(compareName1))

        val birthday2 = BirthdayItemUi.Base(0, "name", "", "")
        val compareName2 = BirthdayItemUiCompareMapper.Name("name")

        assertTrue(birthday2.map(compareName2))
    }

    private fun assertTrueCompareObject(birthday: BirthdayItemUi, mapper: BirthdayItemUiCompareMapper) {
        assertTrue(birthday.map(mapper))
    }

    private fun assertFalseCompareObject(birthday: BirthdayItemUi, mapper: BirthdayItemUiCompareMapper) {
        assertFalse(birthday.map(mapper))
    }

    @Test
    fun test_compare_object() {
        val source = BirthdayItemUi.Base(0, "name", "11 years", "12 days")

        val birthday1 = BirthdayItemUi.Base(1, "name", "11 years", "12 days")
        val birthday2 = BirthdayItemUi.Base(0, "X",    "11 years", "12 days")
        val birthday3 = BirthdayItemUi.Base(0, "name", "X",        "12 days")
        val birthday4 = BirthdayItemUi.Base(0, "name", "11 years", "X")

        assertTrueCompareObject(source, BirthdayItemUiCompareMapper.Object(source))
        assertFalseCompareObject(birthday1, BirthdayItemUiCompareMapper.Object(source))
        assertFalseCompareObject(birthday2, BirthdayItemUiCompareMapper.Object(source))
        assertFalseCompareObject(birthday3, BirthdayItemUiCompareMapper.Object(source))
        assertFalseCompareObject(birthday4, BirthdayItemUiCompareMapper.Object(source))
    }
}