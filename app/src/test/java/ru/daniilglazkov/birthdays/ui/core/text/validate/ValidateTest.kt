package ru.daniilglazkov.birthdays.ui.core.text.validate

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage

/**
 * @author Danil Glazkov on 07.02.2023, 14:19
 */
class ValidateTest {

    private val errorMessage = "Error message"
    private val minLength = 5

    private val validateFirstCharIsLetter = ValidateFirstCharIsLetter(errorMessage)
    private val validateNotEmpty = ValidateNotEmpty(errorMessage)
    private val validateMinLength = ValidateMinLength(minLength, errorMessage)

    @Test
    fun test_validate_min_length_invalid() {
        assertFalse(validateMinLength.isValid(""))
        assertFalse(validateMinLength.isValid("123"))
        assertFalse(validateMinLength.isValid("1234"))
        assertFalse(validateMinLength.isValid("    "))
        assertFalse(validateMinLength.isValid("####"))
    }

    @Test
    fun test_validate_min_length_valid() {
        assertTrue(validateMinLength.isValid("123456"))
        assertTrue(validateMinLength.isValid("12345"))
        assertTrue(validateMinLength.isValid("      "))
        assertTrue(validateMinLength.isValid("     "))
        assertTrue(validateMinLength.isValid("#####"))
    }

    @Test
    fun test_validate_min_length_error_message() {
        assertEquals(ErrorMessage.Base(errorMessage), validateMinLength.errorMessage())
    }

    @Test
    fun test_validate_first_char_is_letter_valid() {
        assertTrue(validateFirstCharIsLetter.isValid("g"))
        assertTrue(validateFirstCharIsLetter.isValid("д"))
        assertTrue(validateFirstCharIsLetter.isValid("g1"))
        assertTrue(validateFirstCharIsLetter.isValid("д!"))
        assertTrue(validateFirstCharIsLetter.isValid("name"))
        assertTrue(validateFirstCharIsLetter.isValid("Name"))
        assertTrue(validateFirstCharIsLetter.isValid("NAME"))
        assertTrue(validateFirstCharIsLetter.isValid("A"))
        assertTrue(validateFirstCharIsLetter.isValid("AAA"))
        assertTrue(validateFirstCharIsLetter.isValid("q-"))
        assertTrue(validateFirstCharIsLetter.isValid("b1"))
    }

    @Test
    fun test_validate_first_char_is_letter_invalid() {
        assertFalse(validateFirstCharIsLetter.isValid(""))
        assertFalse(validateFirstCharIsLetter.isValid("   "))
        assertFalse(validateFirstCharIsLetter.isValid(".g"))
        assertFalse(validateFirstCharIsLetter.isValid(".д"))
        assertFalse(validateFirstCharIsLetter.isValid("1д"))
        assertFalse(validateFirstCharIsLetter.isValid("1L"))
        assertFalse(validateFirstCharIsLetter.isValid("1name"))
        assertFalse(validateFirstCharIsLetter.isValid(" "))
        assertFalse(validateFirstCharIsLetter.isValid("1"))
        assertFalse(validateFirstCharIsLetter.isValid("100"))
        assertFalse(validateFirstCharIsLetter.isValid("!!!"))
        assertFalse(validateFirstCharIsLetter.isValid("."))
        assertFalse(validateFirstCharIsLetter.isValid("/"))
        assertFalse(validateFirstCharIsLetter.isValid("-a"))
    }

    @Test
    fun test_validate_first_char_is_letter_error_message() {
        assertEquals(ErrorMessage.Base(errorMessage), validateFirstCharIsLetter.errorMessage())
    }

    @Test
    fun test_validate_not_empty_valid() {
        assertTrue(validateNotEmpty.isValid("a"))
        assertTrue(validateNotEmpty.isValid("b"))
        assertTrue(validateNotEmpty.isValid("1"))
        assertTrue(validateNotEmpty.isValid("."))
        assertTrue(validateNotEmpty.isValid("/"))
        assertTrue(validateNotEmpty.isValid("123"))
        assertTrue(validateNotEmpty.isValid("name"))
    }

    @Test
    fun test_validate_not_empty_invalid() {
        assertFalse(validateNotEmpty.isValid(""))
        assertFalse(validateNotEmpty.isValid(" "))
        assertFalse(validateNotEmpty.isValid("     "))
    }

    @Test
    fun test_validate_not_empty_error_message() {
        assertEquals(ErrorMessage.Base(errorMessage), validateNotEmpty.errorMessage())
    }
}