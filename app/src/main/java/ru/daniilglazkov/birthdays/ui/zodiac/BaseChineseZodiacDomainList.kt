package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomainList
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 02.11.2022, 09:40
 */
class BaseChineseZodiacDomainList(resources: ProvideString) : ChineseZodiacDomainList {

    private val chineseZodiac = listOf(
        ChineseZodiacDomain.Rat(resources.string(R.string.rat)),
        ChineseZodiacDomain.Ox(resources.string(R.string.ox)),
        ChineseZodiacDomain.Tiger(resources.string(R.string.tiger)),
        ChineseZodiacDomain.Rabbit(resources.string(R.string.rabbit)),
        ChineseZodiacDomain.Dragon(resources.string(R.string.dragon)),
        ChineseZodiacDomain.Snake(resources.string(R.string.snake)),
        ChineseZodiacDomain.Horse(resources.string(R.string.horse)),
        ChineseZodiacDomain.Goat(resources.string(R.string.goat)),
        ChineseZodiacDomain.Monkey(resources.string(R.string.monkey)),
        ChineseZodiacDomain.Rooster(resources.string(R.string.rooster)),
        ChineseZodiacDomain.Dog(resources.string(R.string.dog)),
        ChineseZodiacDomain.Pig(resources.string(R.string.pig)),
    )

    override fun chineseZodiacs(): List<ChineseZodiacDomain> =
        chineseZodiac
}