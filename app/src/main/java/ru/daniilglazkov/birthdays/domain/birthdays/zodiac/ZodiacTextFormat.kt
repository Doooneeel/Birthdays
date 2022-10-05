package ru.daniilglazkov.birthdays.domain.birthdays.zodiac

/**
 * @author Danil Glazkov on 05.10.2022, 22:48
 */
interface ZodiacTextFormat {
    fun format(zodiac: ZodiacRangeCategory): String

    //TODO FIX
    class Base : ZodiacTextFormat {
        override fun format(zodiac: ZodiacRangeCategory): String {
            return when (zodiac) {
                is ZodiacRangeCategory.Aries -> "Овен"
                is ZodiacRangeCategory.Taurus -> "Телец"
                is ZodiacRangeCategory.Gemini -> "Близнецы"
                is ZodiacRangeCategory.Cancer -> "Рак"
                is ZodiacRangeCategory.Lion -> "Лев"
                is ZodiacRangeCategory.Virgo -> "Дева"
                is ZodiacRangeCategory.Libra -> "Весы"
                is ZodiacRangeCategory.Scorpion -> "Скорпион"
                is ZodiacRangeCategory.Sagittarius -> "Стрелец"
                is ZodiacRangeCategory.Capricorn -> "Козерог"
                is ZodiacRangeCategory.Aquarius -> "Водолей"
                is ZodiacRangeCategory.Fish -> "Рыбы"
            }
        }
    }
}