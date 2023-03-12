package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.ui.core.view.AbstractResView

/**
 * @author Danil Glazkov on 02.11.2022, 10:30
 */
interface ZodiacsUi {

    fun applyGreek(name: AbstractResView.Text, image: AbstractResView.Image)

    fun applyChinese(name: AbstractResView.Text)


    abstract class Abstract(
        private val greek: ZodiacUi,
        private val chinese: ZodiacUi,
    ) : ZodiacsUi {
        override fun applyChinese(name: AbstractResView.Text) =
            chinese.apply(name, AbstractResView.Image.Unit)

        override fun applyGreek(name: AbstractResView.Text, image: AbstractResView.Image) =
            greek.apply(name, image)
    }

    data class Base(
        private val greek: ZodiacUi,
        private val chinese: ZodiacUi,
    ) : Abstract(greek, chinese)

    data class Mock(
        private val greek: ZodiacUi = ZodiacUi.Mock(-1),
        private val chinese: ZodiacUi = ZodiacUi.Mock(-2)
    ) : Abstract(greek, chinese)
}