package ru.daniilglazkov.birthdays.domain.showmode.split

import ru.daniilglazkov.birthdays.domain.showmode.age.AgeGroupClassification
import ru.daniilglazkov.birthdays.domain.showmode.age.AgeRangeCategory
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.showmode.group.BirthdayGroupDomain
import ru.daniilglazkov.birthdays.domain.showmode.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.showmode.zodiac.ZodiacRangeCategory
import ru.daniilglazkov.birthdays.domain.core.Split
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import java.time.LocalDate

/**
 * @author Danil Glazkov on 18.08.2022, 19:10
 */
interface SplitBirthdayList : Split<BirthdayListDomain, List<BirthdayGroupDomain>> {

    abstract class Abstract<T>(predicate: BirthdayListSplitPredicate<T>) : SplitBirthdayList {
        private val mapper = BirthdayGroupDomain.Mapper.Split(predicate)

        override fun split(data: BirthdayListDomain): List<BirthdayGroupDomain> =
            data.map(mapper)
    }
    class Month : Abstract<Int>(BirthdayListSplitPredicate.Month())

    class Name : Abstract<Char>(BirthdayListSplitPredicate.FirstCharacter())

    class Year : Abstract<Int>(BirthdayListSplitPredicate.Year())

    class Age(
        before: LocalDate,
        classification: AgeGroupClassification
    ) : Abstract<AgeRangeCategory>(
        BirthdayListSplitPredicate.Age(before, classification)
    )

    class MonthsByYears(nextEvent: NextEvent) : Abstract<String>(
        BirthdayListSplitPredicate.MonthsByYears(nextEvent)
    )

    class Zodiac(zodiacRangeCategory: ZodiacGroupClassification) : Abstract<ZodiacRangeCategory>(
        BirthdayListSplitPredicate.Zodiac(zodiacRangeCategory)
    )

}