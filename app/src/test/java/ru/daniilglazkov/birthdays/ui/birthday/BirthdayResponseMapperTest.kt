package ru.daniilglazkov.birthdays.ui.birthday

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacsDomain
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsUi
import java.time.LocalDate

/**
 * @author Danil Glazkov on 04.03.2023, 11:26
 */
class BirthdayResponseMapperTest : BaseUiTest() {

    private lateinit var communications: TestBirthdayCommunications
    private lateinit var sheetCommunication: TestSheetCommunication
    private lateinit var birthdayDomainToUiMapper: TestBirthdayDomainToUiMapper
    private lateinit var zodiacsDomainToUiMapper: TestZodiacsDomainToUiMapper
    private lateinit var handleError: TestHandleError

    private lateinit var responseMapper: BirthdayResponseMapper


    @Before
    fun setUp() {
        communications = TestBirthdayCommunications()
        sheetCommunication = TestSheetCommunication()
        birthdayDomainToUiMapper = TestBirthdayDomainToUiMapper()
        zodiacsDomainToUiMapper = TestZodiacsDomainToUiMapper()
        handleError = TestHandleError()

        responseMapper = BirthdayResponseMapper.Base(
            communications,
            birthdayDomainToUiMapper,
            zodiacsDomainToUiMapper,
            sheetCommunication,
            handleError
        )
    }

    @Test
    fun test_exception() {
        handleError.result = "error"

        val firstException = Exception()
        val secondException = IllegalStateException()

        responseMapper.map(firstException)

        assertEquals(1, sheetCommunication.mapCalledList.size)

        assertEquals(1, communications.putErrorMessageCalledList.size)
        assertEquals(ErrorMessage.Base("error"), communications.putErrorMessageCalledList[0])

        assertEquals(firstException, handleError.calledList[0])

        responseMapper.map(secondException)

        assertEquals(secondException, handleError.calledList[1])

        assertEquals(2, handleError.calledList.size)
        assertEquals(2, sheetCommunication.mapCalledList.size)

        assertEquals(2, communications.putErrorMessageCalledList.size)
        assertEquals(ErrorMessage.Base("error"), communications.putErrorMessageCalledList[1])
    }

    @Test
    fun test_success() {
        birthdayDomainToUiMapper.result = BirthdayUi.Base("a", "b", "c", "d", DaysLeft.Base("", ""))
        zodiacsDomainToUiMapper.result = ZodiacsUi.Mock()

        responseMapper.map(BirthdayDomain.Mock(), ZodiacsDomain.Mock())

        assertEquals(1, birthdayDomainToUiMapper.callCount)

        assertEquals(1, communications.putBirthdayCalledList.size)
        assertEquals(birthdayDomainToUiMapper.result, communications.putBirthdayCalledList[0])


        assertEquals(1, zodiacsDomainToUiMapper.callCount)

        assertEquals(1, communications.putZodiacsCalledList.size)
        assertEquals(zodiacsDomainToUiMapper.result, communications.putZodiacsCalledList[0])


        assertEquals(0, communications.putErrorMessageCalledList.size)
        assertEquals(0, sheetCommunication.mapCalledList.size)
    }


    private class TestZodiacsDomainToUiMapper : ZodiacsDomainToUiMapper {

        lateinit var result: ZodiacsUi
        var callCount = 0

        override fun map(greek: ZodiacDomain, chinese: ZodiacDomain): ZodiacsUi {
            ++callCount
            return result
        }
    }

    private class TestBirthdayDomainToUiMapper : BirthdayDomainToUiMapper {

        lateinit var result: BirthdayUi
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi {
            ++callCount
            return result
        }
    }
}