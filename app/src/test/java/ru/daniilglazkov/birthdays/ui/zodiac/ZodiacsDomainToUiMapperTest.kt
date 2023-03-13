package ru.daniilglazkov.birthdays.ui.zodiac

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain

/**
 * @author Danil Glazkov on 08.03.2023, 19:13
 */
class ZodiacsDomainToUiMapperTest {

    @Test
    fun test_map() {
        val greek = TestZodiacDomainToUiMapper()
        val chinese = TestZodiacDomainToUiMapper()

        greek.result = ZodiacUi.Mock(10)
        chinese.result = ZodiacUi.Mock(15)

        val mapper = ZodiacsDomainToUiMapper.Base(greek, chinese)

        assertEquals(
            ZodiacsUi.Base(greek.result, chinese.result),
            mapper.map(GreekZodiacDomain.Base(2, ""), ChineseZodiacDomain.Base(4, ""))
        )
        assertEquals(1, greek.callCount.size)
        assertEquals(2, greek.callCount[0])
        assertEquals(1, chinese.callCount.size)
        assertEquals(4, chinese.callCount[0])
    }


    private class TestZodiacDomainToUiMapper : ZodiacDomainToUiMapper {

        val callCount = mutableListOf<Int>()
        lateinit var result: ZodiacUi

        override fun map(ordinal: Int, name: String): ZodiacUi {
            callCount.add(ordinal)
            return result
        }
    }

}