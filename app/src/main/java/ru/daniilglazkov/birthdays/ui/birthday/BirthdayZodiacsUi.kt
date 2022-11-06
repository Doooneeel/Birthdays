package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.ui.core.view.AbstractResView
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacUi

/**
 * @author Danil Glazkov on 02.11.2022, 10:30
 */
interface BirthdayZodiacsUi {

    fun apply(name: AbstractResView.Text, image: AbstractResView.Image)
    fun applyChinese(name: AbstractResView.Text)


    class Base(private val base: ZodiacUi, private val chinese: ZodiacUi) : BirthdayZodiacsUi {
        override fun apply(name: AbstractResView.Text, image: AbstractResView.Image) =
            base.apply(name, image)

        override fun applyChinese(name: AbstractResView.Text) =
            chinese.apply(name, AbstractResView.Image.Unit)
    }
}