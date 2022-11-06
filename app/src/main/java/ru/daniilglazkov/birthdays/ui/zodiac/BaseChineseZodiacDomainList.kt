package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.zodiac.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ChineseZodiacDomain.*
import ru.daniilglazkov.birthdays.domain.zodiac.FetchChineseZodiacDomainList
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 02.11.2022, 09:40
 */
class BaseChineseZodiacDomainList(resources: ProvideString) : FetchChineseZodiacDomainList {
    private val chineseZodiac by lazy {
        listOf(Rat(resources.string(R.string.rat)),
            Ox(resources.string(R.string.ox)),
            Tiger(resources.string(R.string.tiger)),
            Rabbit(resources.string(R.string.rabbit)),
            Dragon(resources.string(R.string.dragon)),
            Snake(resources.string(R.string.snake)),
            Horse(resources.string(R.string.horse)),
            Goat(resources.string(R.string.goat)),
            Monkey(resources.string(R.string.monkey)),
            Rooster(resources.string(R.string.rooster)),
            Dog(resources.string(R.string.dog)),
            Pig(resources.string(R.string.pig)),
        )
    }
    override fun chineseZodiacs(): List<ChineseZodiacDomain> = chineseZodiac
}