package ru.daniilglazkov.birthdays.domain.birthdaylist.transform

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeGroups
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeRange
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.domain.zodiac.greek.*
import java.time.LocalDate
import java.time.Month
import java.util.Locale

/**
 * @author Danil Glazkov on 03.03.2023, 15:02
 */
class TransformBirthdayListFactoryTest {

    private val now = LocalDate.of(2020, 5, 15)
    private val locale = Locale("ru")
    private val dateStub = LocalDate.MIN

    //region NAME

    private val nameMapper = TransformBirthdayListFactory.Name(Locale.ENGLISH)

    private val nameTestList = listOf(
        BirthdayDomain.Base(2, "123", dateStub),
        BirthdayDomain.Base(3, "111", dateStub),
        BirthdayDomain.Base(4, "Aaa", dateStub),
        BirthdayDomain.Base(5, "aaa", dateStub),
        BirthdayDomain.Base(1, "", dateStub),
        BirthdayDomain.Base(9, "vaa", dateStub),
        BirthdayDomain.Base(10, "vbb", dateStub),
        BirthdayDomain.Base(6, "BBB", dateStub),
        BirthdayDomain.Base(7, "b", dateStub),
        BirthdayDomain.Base(8, "1123", dateStub),
    )


    @Test
    fun test_name_group_false() {
        val expectedList = listOf(
            BirthdayDomain.Base(1, "", dateStub),
            BirthdayDomain.Base(3, "111", dateStub),
            BirthdayDomain.Base(8, "1123", dateStub),
            BirthdayDomain.Base(2, "123", dateStub),
            BirthdayDomain.Base(4, "Aaa", dateStub),
            BirthdayDomain.Base(5, "aaa", dateStub),
            BirthdayDomain.Base(7, "b", dateStub),
            BirthdayDomain.Base(6, "BBB", dateStub),
            BirthdayDomain.Base(9, "vaa", dateStub),
            BirthdayDomain.Base(10, "vbb", dateStub),
        )

        testTransform(
            transformBirthdayList = nameMapper.map(SortMode.None, reverse = false, group = false),
            sourceList = nameTestList,
            expectedList = expectedList,
        )

        testTransform(
            transformBirthdayList = nameMapper.map(SortMode.None, reverse = true, group = false),
            sourceList = nameTestList,
            expectedList = expectedList.reversed(),
        )
    }

    @Test
    fun test_name_group_true_reverse_false() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, ""),
            BirthdayDomain.Base(1, "", dateStub),
            BirthdayDomain.Header(-2, "1"),
            BirthdayDomain.Base(3, "111", dateStub),
            BirthdayDomain.Base(8, "1123", dateStub),
            BirthdayDomain.Base(2, "123", dateStub),
            BirthdayDomain.Header(-3, "A"),
            BirthdayDomain.Base(4, "Aaa", dateStub),
            BirthdayDomain.Base(5, "aaa", dateStub),
            BirthdayDomain.Header(-4, "B"),
            BirthdayDomain.Base(7, "b", dateStub),
            BirthdayDomain.Base(6, "BBB", dateStub),
            BirthdayDomain.Header(-5, "V"),
            BirthdayDomain.Base(9, "vaa", dateStub),
            BirthdayDomain.Base(10, "vbb", dateStub),
        )

        testTransform(
            transformBirthdayList = nameMapper.map(SortMode.None, reverse = false, group = true),
            sourceList = nameTestList,
            expectedList = expectedList,
        )
    }

    @Test
    fun test_name_group_true_reverse_true() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "V"),
            BirthdayDomain.Base(10, "vbb", dateStub),
            BirthdayDomain.Base(9, "vaa", dateStub),
            BirthdayDomain.Header(-2, "B"),
            BirthdayDomain.Base(6, "BBB", dateStub),
            BirthdayDomain.Base(7, "b", dateStub),
            BirthdayDomain.Header(-3, "A"),
            BirthdayDomain.Base(5, "aaa", dateStub),
            BirthdayDomain.Base(4, "Aaa", dateStub),
            BirthdayDomain.Header(-4, "1"),
            BirthdayDomain.Base(2, "123", dateStub),
            BirthdayDomain.Base(8, "1123", dateStub),
            BirthdayDomain.Base(3, "111", dateStub),
            BirthdayDomain.Header(-5, ""),
            BirthdayDomain.Base(1, "", dateStub),
        )

        testTransform(
            transformBirthdayList = nameMapper.map(SortMode.None, reverse = true, group = true),
            sourceList = nameTestList,
            expectedList = expectedList,
        )
    }

    //endregion NAME

    //region YEAR

    private val yearMapper = TransformBirthdayListFactory.Year()

    private val yearTestList = listOf(
        BirthdayDomain.Base(1, "", LocalDate.of(2004, 12, 6)),
        BirthdayDomain.Base(2, "", LocalDate.of(1998, 6, 3)),
        BirthdayDomain.Base(3, "", LocalDate.of(1998, 1, 12)),
        BirthdayDomain.Base(4, "", LocalDate.of(2007, 3, 20)),
        BirthdayDomain.Base(5, "", LocalDate.of(2000, 11, 9)),
        BirthdayDomain.Base(6, "", LocalDate.of(2000, 2, 22)),
        BirthdayDomain.Base(7, "", LocalDate.of(2013, 9, 12)),
        BirthdayDomain.Base(8, "", LocalDate.of(2013, 4, 15)),
        BirthdayDomain.Base(9, "", LocalDate.of(2001, 2, 12)),
        BirthdayDomain.Base(10, "", LocalDate.of(1998, 6, 22)),
    )


    @Test
    fun test_year_group_false() {
        val expectedList = listOf(
            BirthdayDomain.Base(3, "", LocalDate.of(1998, 1, 12)),
            BirthdayDomain.Base(2, "", LocalDate.of(1998, 6, 3)),
            BirthdayDomain.Base(10, "", LocalDate.of(1998, 6, 22)),
            BirthdayDomain.Base(6, "", LocalDate.of(2000, 2, 22)),
            BirthdayDomain.Base(5, "", LocalDate.of(2000, 11, 9)),
            BirthdayDomain.Base(9, "", LocalDate.of(2001, 2, 12)),
            BirthdayDomain.Base(1, "", LocalDate.of(2004, 12, 6)),
            BirthdayDomain.Base(4, "", LocalDate.of(2007, 3, 20)),
            BirthdayDomain.Base(8, "", LocalDate.of(2013, 4, 15)),
            BirthdayDomain.Base(7, "", LocalDate.of(2013, 9, 12)),
        )

        testTransform(
            transformBirthdayList = yearMapper.map(SortMode.None, reverse = false, group = false),
            sourceList = yearTestList,
            expectedList = expectedList,
        )

        testTransform(
            transformBirthdayList = yearMapper.map(SortMode.None, reverse = true, group = false),
            sourceList = yearTestList,
            expectedList = expectedList.reversed(),
        )
    }

    @Test
    fun test_year_group_true_reverse_false() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "1998"),
            BirthdayDomain.Base(3, "", LocalDate.of(1998, 1, 12)),
            BirthdayDomain.Base(2, "", LocalDate.of(1998, 6, 3)),
            BirthdayDomain.Base(10, "", LocalDate.of(1998, 6, 22)),
            BirthdayDomain.Header(-2, "2000"),
            BirthdayDomain.Base(6, "", LocalDate.of(2000, 2, 22)),
            BirthdayDomain.Base(5, "", LocalDate.of(2000, 11, 9)),
            BirthdayDomain.Header(-3, "2001"),
            BirthdayDomain.Base(9, "", LocalDate.of(2001, 2, 12)),
            BirthdayDomain.Header(-4, "2004"),
            BirthdayDomain.Base(1, "", LocalDate.of(2004, 12, 6)),
            BirthdayDomain.Header(-5, "2007"),
            BirthdayDomain.Base(4, "", LocalDate.of(2007, 3, 20)),
            BirthdayDomain.Header(-6, "2013"),
            BirthdayDomain.Base(8, "", LocalDate.of(2013, 4, 15)),
            BirthdayDomain.Base(7, "", LocalDate.of(2013, 9, 12)),
        )

        testTransform(
            transformBirthdayList = yearMapper.map(SortMode.None, reverse = false, group = true),
            sourceList = yearTestList,
            expectedList = expectedList,
        )
    }

    @Test
    fun test_year_group_true_reverse_true() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "2013"),
            BirthdayDomain.Base(7, "", LocalDate.of(2013, 9, 12)),
            BirthdayDomain.Base(8, "", LocalDate.of(2013, 4, 15)),
            BirthdayDomain.Header(-2, "2007"),
            BirthdayDomain.Base(4, "", LocalDate.of(2007, 3, 20)),
            BirthdayDomain.Header(-3, "2004"),
            BirthdayDomain.Base(1, "", LocalDate.of(2004, 12, 6)),
            BirthdayDomain.Header(-4, "2001"),
            BirthdayDomain.Base(9, "", LocalDate.of(2001, 2, 12)),
            BirthdayDomain.Header(-5, "2000"),
            BirthdayDomain.Base(5, "", LocalDate.of(2000, 11, 9)),
            BirthdayDomain.Base(6, "", LocalDate.of(2000, 2, 22)),
            BirthdayDomain.Header(-6, "1998"),
            BirthdayDomain.Base(10, "", LocalDate.of(1998, 6, 22)),
            BirthdayDomain.Base(2, "", LocalDate.of(1998, 6, 3)),
            BirthdayDomain.Base(3, "", LocalDate.of(1998, 1, 12)),
        )

        testTransform(
            transformBirthdayList = yearMapper.map(SortMode.None, reverse = true, group = true),
            sourceList = yearTestList,
            expectedList = expectedList,
        )
    }

    //endregion YEAR

    //region MONTH

    private val monthMapper = TransformBirthdayListFactory.Month(locale)

    private val monthTestList = listOf(
        BirthdayDomain.Base(1, "", LocalDate.of(1, Month.DECEMBER, 1)),
        BirthdayDomain.Base(2, "", LocalDate.of(1, Month.DECEMBER, 1)),
        BirthdayDomain.Base(3, "", LocalDate.of(1, Month.MAY, 1)),
        BirthdayDomain.Base(4, "", LocalDate.of(1, Month.JANUARY, 1)),
        BirthdayDomain.Base(5, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
        BirthdayDomain.Base(6, "", LocalDate.of(1, Month.DECEMBER, 1)),
        BirthdayDomain.Base(7, "", LocalDate.of(1, Month.MAY, 1)),
        BirthdayDomain.Base(8, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
        BirthdayDomain.Base(9, "", LocalDate.of(1, Month.APRIL, 1)),
        BirthdayDomain.Base(10, "", LocalDate.of(1, Month.MARCH, 1)),
    )


    @Test
    fun test_month_group_false() {
        val expectedList = listOf(
            BirthdayDomain.Base(4, "", LocalDate.of(1, Month.JANUARY, 1)),
            BirthdayDomain.Base(10, "", LocalDate.of(1, Month.MARCH, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(1, Month.APRIL, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(1, Month.MAY, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1, Month.MAY, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(1, Month.DECEMBER, 1)),
        )

        testTransform(
            transformBirthdayList = monthMapper.map(SortMode.None, reverse = false, group = false),
            sourceList = monthTestList,
            expectedList = expectedList,
        )

        testTransform(
            transformBirthdayList = monthMapper.map(SortMode.None, reverse = true, group = false),
            sourceList = monthTestList,
            expectedList = expectedList.reversed(),
        )
    }

    @Test
    fun test_month_group_true_reverse_false() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "Январь"),
            BirthdayDomain.Base(4, "", LocalDate.of(1, Month.JANUARY, 1)),
            BirthdayDomain.Header(-2, "Март"),
            BirthdayDomain.Base(10, "", LocalDate.of(1, Month.MARCH, 1)),
            BirthdayDomain.Header(-3, "Апрель"),
            BirthdayDomain.Base(9, "", LocalDate.of(1, Month.APRIL, 1)),
            BirthdayDomain.Header(-4, "Май"),
            BirthdayDomain.Base(3, "", LocalDate.of(1, Month.MAY, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1, Month.MAY, 1)),
            BirthdayDomain.Header(-5, "Сентябрь"),
            BirthdayDomain.Base(5, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
            BirthdayDomain.Header(-6, "Декабрь"),
            BirthdayDomain.Base(1, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(1, Month.DECEMBER, 1)),
        )

        testTransform(
            transformBirthdayList = monthMapper.map(SortMode.None, reverse = false, group = true),
            sourceList = monthTestList,
            expectedList = expectedList,
        )
    }

    @Test
    fun test_month_group_true_reverse_true() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "Декабрь"),
            BirthdayDomain.Base(6, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(1, Month.DECEMBER, 1)),
            BirthdayDomain.Header(-2, "Сентябрь"),
            BirthdayDomain.Base(8, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(1, Month.SEPTEMBER, 1)),
            BirthdayDomain.Header(-3, "Май"),
            BirthdayDomain.Base(7, "", LocalDate.of(1, Month.MAY, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(1, Month.MAY, 1)),
            BirthdayDomain.Header(-4, "Апрель"),
            BirthdayDomain.Base(9, "", LocalDate.of(1, Month.APRIL, 1)),
            BirthdayDomain.Header(-5, "Март"),
            BirthdayDomain.Base(10, "", LocalDate.of(1, Month.MARCH, 1)),
            BirthdayDomain.Header(-6, "Январь"),
            BirthdayDomain.Base(4, "", LocalDate.of(1, Month.JANUARY, 1)),
        )

        testTransform(
            transformBirthdayList = monthMapper.map(SortMode.None, reverse = true, group = true),
            sourceList = monthTestList,
            expectedList = expectedList,
        )
    }

    //endregion MONTH

    //region AGE

    private val ageMapper = TransformBirthdayListFactory.Age(
        AgeGroups.Base(listOf(
            AgeRange.Base(0..30),
            AgeRange.Base(31..90),
            AgeRange.Base(91..Int.MAX_VALUE))
        ),
        now
    )

    private val ageTestList = listOf(
        BirthdayDomain.Base(1, "", LocalDate.of(2012, 2, 1)),
        BirthdayDomain.Base(2, "", LocalDate.of(1982, 4, 1)),
        BirthdayDomain.Base(3, "", LocalDate.of(2001, 2, 1)),
        BirthdayDomain.Base(4, "", LocalDate.of(1960, 10, 1)),
        BirthdayDomain.Base(5, "", LocalDate.of(2012, 1, 1)),
        BirthdayDomain.Base(6, "", LocalDate.of(2003, 5, 1)),
        BirthdayDomain.Base(7, "", LocalDate.of(1982, 5, 1)),
        BirthdayDomain.Base(8, "", LocalDate.of(2008, 9, 1)),
        BirthdayDomain.Base(9, "", LocalDate.of(2002, 4, 1)),
        BirthdayDomain.Base(10, "", LocalDate.of(2015, 2, 1)),
        BirthdayDomain.Base(11, "", LocalDate.of(1922, 3, 12)),
    )


    @Test
    fun test_age_group_false() {
        val expectedList = listOf(
            BirthdayDomain.Base(10, "", LocalDate.of(2015, 2, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(2012, 2, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(2012, 1, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(2008, 9, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(2003, 5, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(2002, 4, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(2001, 2, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1982, 5, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1982, 4, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1960, 10, 1)),
            BirthdayDomain.Base(11, "", LocalDate.of(1922, 3, 12)),
        )

        testTransform(
            transformBirthdayList = ageMapper.map(SortMode.None, reverse = false, group = false),
            sourceList = ageTestList,
            expectedList = expectedList,
        )

        testTransform(
            transformBirthdayList = ageMapper.map(SortMode.None, reverse = true, group = false),
            sourceList = ageTestList,
            expectedList = expectedList.reversed(),
        )
    }

    @Test
    fun test_age_group_true_reverse_false() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "6 — 20"),
            BirthdayDomain.Base(10, "", LocalDate.of(2015, 2, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(2012, 2, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(2012, 1, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(2008, 9, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(2003, 5, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(2002, 4, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(2001, 2, 1)),
            BirthdayDomain.Header(-2, "39 — 60"),
            BirthdayDomain.Base(7, "", LocalDate.of(1982, 5, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1982, 4, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1960, 10, 1)),
            BirthdayDomain.Header(-3, "99"),
            BirthdayDomain.Base(11, "", LocalDate.of(1922, 3, 12)),
        )

        testTransform(
            transformBirthdayList = ageMapper.map(SortMode.None, reverse = false, group = true),
            sourceList = ageTestList,
            expectedList = expectedList,
        )
    }

    @Test
    fun test_age_group_true_reverse_true() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "99"),
            BirthdayDomain.Base(11, "", LocalDate.of(1922, 3, 12)),
            BirthdayDomain.Header(-2, "60 — 39"),
            BirthdayDomain.Base(4, "", LocalDate.of(1960, 10, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1982, 4, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1982, 5, 1)),
            BirthdayDomain.Header(-3, "20 — 6"),
            BirthdayDomain.Base(3, "", LocalDate.of(2001, 2, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(2002, 4, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(2003, 5, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(2008, 9, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(2012, 1, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(2012, 2, 1)),
            BirthdayDomain.Base(10, "", LocalDate.of(2015, 2, 1)),
        )

        testTransform(
            transformBirthdayList = ageMapper.map(SortMode.None, reverse = true, group = true),
            sourceList = ageTestList,
            expectedList = expectedList,
        )
    }

    //endregion AGE

    //region ZODIAC

    private val zodiacMapper = TransformBirthdayListFactory.Zodiac(
        GreekZodiacGroups.Base(object : GreekZodiacDomainList {
            override fun greekZodiacs() = listOf(
                GreekZodiacDomain.Base(0, "zodiac_1", 10..182),
                GreekZodiacDomain.Base(1, "zodiac_2", 182..366),
                GreekZodiacDomain.Base(0, "zodiac_3", 0..9),
            )
        })
    )

    private val zodiacTestList = listOf(
        BirthdayDomain.Base(1, "", LocalDate.of(1, 1, 1)),
        BirthdayDomain.Base(2, "", LocalDate.of(1, 1, 1)),
        BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
        BirthdayDomain.Base(4, "", LocalDate.of(1, 4, 1)),
        BirthdayDomain.Base(5, "", LocalDate.of(1, 5, 1)),
        BirthdayDomain.Base(6, "", LocalDate.of(1, 1, 5)),
        BirthdayDomain.Base(7, "", LocalDate.of(1, 5, 1)),
        BirthdayDomain.Base(8, "", LocalDate.of(1, 4, 1)),
        BirthdayDomain.Base(9, "", LocalDate.of(1, 3, 1)),
        BirthdayDomain.Base(10, "", LocalDate.of(1, 2, 1)),
        BirthdayDomain.Base(11, "", LocalDate.of(1, 12, 1)),
        BirthdayDomain.Base(12, "", LocalDate.of(1, 11, 1)),
    )


    @Test
    fun test_zodiac_group_false() {
        val expectedList = listOf(
            BirthdayDomain.Base(1, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1, 4, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(1, 5, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(1, 1, 5)),
            BirthdayDomain.Base(7, "", LocalDate.of(1, 5, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(1, 4, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(10, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(11, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Base(12, "", LocalDate.of(1, 11, 1)),
        )

        testTransform(
            transformBirthdayList = zodiacMapper.map(SortMode.None, reverse = false, group = false),
            sourceList = zodiacTestList,
            expectedList = expectedList,
        )

        testTransform(
            transformBirthdayList = zodiacMapper.map(SortMode.None, reverse = true, group = false),
            sourceList = zodiacTestList,
            expectedList = expectedList.reversed(),
        )
    }

    @Test
    fun test_zodiac_group_true_reverse_false() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "zodiac_3"),
            BirthdayDomain.Base(1, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(1, 1, 5)),
            BirthdayDomain.Header(-2, "zodiac_1"),
            BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1, 4, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(1, 5, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1, 5, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(1, 4, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(10, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Header(-3, "zodiac_2"),
            BirthdayDomain.Base(11, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Base(12, "", LocalDate.of(1, 11, 1)),
        )

        testTransform(
            transformBirthdayList = zodiacMapper.map(SortMode.None, reverse = false, group = true),
            sourceList = zodiacTestList,
            expectedList = expectedList
        )
    }

    @Test
    fun test_zodiac_group_true_reverse_true() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "zodiac_2"),
            BirthdayDomain.Base(12, "", LocalDate.of(1, 11, 1)),
            BirthdayDomain.Base(11, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Header(-2, "zodiac_1"),
            BirthdayDomain.Base(10, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(1, 4, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1, 5, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(1, 5, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1, 4, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Header(-3, "zodiac_3"),
            BirthdayDomain.Base(6, "", LocalDate.of(1, 1, 5)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(1, 1, 1)),
        )

        testTransform(
            transformBirthdayList = zodiacMapper.map(SortMode.None, reverse = true, group = true),
            sourceList = zodiacTestList,
            expectedList = expectedList,
        )
    }

    //endregion ZODIAC

    //region DATE

    private val dateMapper = TransformBirthdayListFactory.Date(
        CalculateNextEvent.Base(
            EventIsToday.Base(now),
            IsLeapDay.Base(),
            DetermineLeapDay.Base(),
            now
        ),
        locale,
        now
    )

    private val dateList = listOf(
        BirthdayDomain.Base(1, "", LocalDate.of(1, 3, 1)),
        BirthdayDomain.Base(2, "", LocalDate.of(1, 2, 1)),
        BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
        BirthdayDomain.Base(4, "", LocalDate.of(1, 12, 1)),
        BirthdayDomain.Base(5, "", LocalDate.of(1, 1, 1)),
        BirthdayDomain.Base(6, "", LocalDate.of(1, 7, 1)),
        BirthdayDomain.Base(7, "", LocalDate.of(1, 8, 1)),
        BirthdayDomain.Base(8, "", LocalDate.of(1, 9, 1)),
        BirthdayDomain.Base(9, "", LocalDate.of(1, 7, 1)),
        BirthdayDomain.Base(10, "", LocalDate.of(1, 12, 1)),
    )


    @Test
    fun test_date_group_false() {
        val expectedList = listOf(
            BirthdayDomain.Base(6, "", LocalDate.of(1, 7, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(1, 7, 1)),
            BirthdayDomain.Base(7, "", LocalDate.of(1, 8, 1)),
            BirthdayDomain.Base(8, "", LocalDate.of(1, 9, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Base(10, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Base(5, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(2, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
        )

        testTransform(
            transformBirthdayList = dateMapper.map(SortMode.None, reverse = false, group = false),
            sourceList = dateList,
            expectedList = expectedList,
        )

        testTransform(
            transformBirthdayList = dateMapper.map(SortMode.None, reverse = true, group = false),
            sourceList = dateList,
            expectedList = expectedList.reversed(),
        )
    }

    @Test
    fun test_date_group_true_reverse_false() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "Июль 2020"),
            BirthdayDomain.Base(6, "", LocalDate.of(1, 7, 1)),
            BirthdayDomain.Base(9, "", LocalDate.of(1, 7, 1)),
            BirthdayDomain.Header(-2, "Август 2020"),
            BirthdayDomain.Base(7, "", LocalDate.of(1, 8, 1)),
            BirthdayDomain.Header(-3, "Сентябрь 2020"),
            BirthdayDomain.Base(8, "", LocalDate.of(1, 9, 1)),
            BirthdayDomain.Header(-4, "Декабрь 2020"),
            BirthdayDomain.Base(4, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Base(10, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Header(-5, "Январь 2021"),
            BirthdayDomain.Base(5, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Header(-6, "Февраль 2021"),
            BirthdayDomain.Base(2, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Header(-7, "Март 2021"),
            BirthdayDomain.Base(1, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
        )

        testTransform(
            transformBirthdayList = dateMapper.map(SortMode.None, reverse = false, group = true),
            sourceList = dateList,
            expectedList = expectedList,
        )
    }

    @Test
    fun test_date_group_true_reverse_true() {
        val expectedList = listOf(
            BirthdayDomain.Header(-1, "Март 2021"),
            BirthdayDomain.Base(3, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(1, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Header(-2, "Февраль 2021"),
            BirthdayDomain.Base(2, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Header(-3, "Январь 2021"),
            BirthdayDomain.Base(5, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Header(-4, "Декабрь 2020"),
            BirthdayDomain.Base(10, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Base(4, "", LocalDate.of(1, 12, 1)),
            BirthdayDomain.Header(-5, "Сентябрь 2020"),
            BirthdayDomain.Base(8, "", LocalDate.of(1, 9, 1)),
            BirthdayDomain.Header(-6, "Август 2020"),
            BirthdayDomain.Base(7, "", LocalDate.of(1, 8, 1)),
            BirthdayDomain.Header(-7, "Июль 2020"),
            BirthdayDomain.Base(9, "", LocalDate.of(1, 7, 1)),
            BirthdayDomain.Base(6, "", LocalDate.of(1, 7, 1)),
        )

        testTransform(
            transformBirthdayList = dateMapper.map(SortMode.None, reverse = true, group = true),
            sourceList = dateList,
            expectedList = expectedList,
        )
    }

    //endregion DATE


    private fun testTransform(
        transformBirthdayList: TransformBirthdayList,
        sourceList: List<BirthdayDomain>,
        expectedList: List<BirthdayDomain>,
    ) {
        val emptyBirthdayList = BirthdayListDomain.Base()

        assertEquals(emptyBirthdayList, transformBirthdayList.transform(emptyBirthdayList))
        assertEquals(
            BirthdayListDomain.Base(expectedList),
            transformBirthdayList.transform(BirthdayListDomain.Base(sourceList))
        )
    }
}