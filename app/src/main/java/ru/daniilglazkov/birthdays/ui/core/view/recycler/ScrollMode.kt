package ru.daniilglazkov.birthdays.ui.core.view.recycler

/**
 * @author Danil Glazkov on 17.11.2022, 20:07
 */
interface ScrollMode {

    fun scroll(recyclerViewScroll: RecyclerViewScroll.Combo)


    abstract class AbstractAnimated(private val position: Int): ScrollMode {
        override fun scroll(recyclerViewScroll: RecyclerViewScroll.Combo) =
            recyclerViewScroll.smoothScroll(position)
    }

    abstract class AbstractSharp(private val position: Int): ScrollMode {
        override fun scroll(recyclerViewScroll: RecyclerViewScroll.Combo) =
            recyclerViewScroll.sharpScroll(position)
    }

    class Animated(position: Int) : AbstractAnimated(position)

    object AnimatedUp : AbstractAnimated(position =  0)


    object SharpUp : AbstractSharp(position = 0)

    class Sharp(position: Int) : AbstractSharp(position)


    object NoScroll : ScrollMode {
        override fun scroll(recyclerViewScroll: RecyclerViewScroll.Combo) = Unit
    }
}