package ru.daniilglazkov.birthdays.ui.newbirthday.validation

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.text.validate.Validate
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayUi
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.03.2023, 17:01
 */
class NewBirthdayValidateMapperTest {

    private val name = "a"
    private val date = LocalDate.MIN

    @Test
    fun test_valid() {
        val validate = TestValidate()
        validate.isValid = true

        val mapper = NewBirthdayValidateMapper.Base(validate)

        val expected = NewBirthdayValidationResult.Valid(NewBirthdayUi.Base(name, date))
        val actual: NewBirthdayValidationResult = mapper.map(name, date)

        assertEquals(expected, actual)

        assertEquals(1, validate.validCalledList.size)
        assertEquals(name, validate.validCalledList[0])

        assertEquals(0, validate.errorCallCount)
    }

    @Test
    fun test_invalid() {
        val errorMessage = ErrorMessage.Base("error")
        val validate = TestValidate()

        validate.isValid = false
        validate.errorMessage = errorMessage

        val mapper = NewBirthdayValidateMapper.Base(validate)

        val expected = NewBirthdayValidationResult.Invalid(errorMessage)
        val actual: NewBirthdayValidationResult = mapper.map(name, date)

        assertEquals(expected, actual)

        assertEquals(1, validate.validCalledList.size)
        assertEquals(name, validate.validCalledList[0])

        assertEquals(1, validate.errorCallCount)
    }


    private class TestValidate : Validate {

        lateinit var errorMessage: ErrorMessage
        val validCalledList = mutableListOf<String>()
        var errorCallCount = 0
        var isValid = false

        override fun isValid(text: String): Boolean {
            validCalledList.add(text)
            return isValid
        }

        override fun errorMessage(): ErrorMessage {
            ++errorCallCount
            return errorMessage
        }
    }
}