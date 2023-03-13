package ru.daniilglazkov.birthdays.ui.core.text.validate

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage

/**
 * @author Danil Glazkov on 07.02.2023, 14:00
 */
class ValidateChainTest {

    private lateinit var baseValidate: TestValidate
    private lateinit var nextValidate: TestValidate
    private lateinit var chain: ValidateChain

    @Before
    fun setUp() {
        baseValidate = TestValidate()
        nextValidate = TestValidate()

        baseValidate.errorMessage = "base error message"
        nextValidate.errorMessage = "next error message"

        chain = ValidateChain(baseValidate, nextValidate)
    }

    @Test
    fun test_init_error_message() {
        assertEquals(ErrorMessage.Base(baseValidate.errorMessage), chain.errorMessage())
    }

    @Test
    fun test_validate_base_valid() {
        baseValidate.isValid = true

        chain.isValid("")

        assertEquals(1, nextValidate.isValidCalledList.size)
        assertEquals(1, baseValidate.isValidCalledList.size)

        assertEquals(ErrorMessage.Base(nextValidate.errorMessage), chain.errorMessage())
    }

    @Test
    fun test_validate_base_invalid() {
        baseValidate.isValid = false

        chain.isValid("")

        assertEquals(1, baseValidate.isValidCalledList.size)
        assertEquals(0, nextValidate.isValidCalledList.size)

        assertEquals(ErrorMessage.Base(baseValidate.errorMessage), chain.errorMessage())
    }
}