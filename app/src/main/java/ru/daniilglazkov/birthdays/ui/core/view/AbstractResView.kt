package ru.daniilglazkov.birthdays.ui.core.view

/**
 * @author Danil Glazkov on 27.09.2022, 14:25
 */
interface AbstractResView : AbstractView<Int> {

    interface Text : AbstractResView

    interface Image : AbstractResView {

        object Unit : Image {
            override fun map(source: Int) = kotlin.Unit
        }

    }

}