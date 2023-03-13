package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import ru.daniilglazkov.birthdays.ui.core.view.recycler.ScrollMode

/**
 * @author Danil Glazkov on 14.02.2023, 16:37
 */
class RecyclerStateTest {

    @Test
    fun test_apply() {
        val recyclerView = TestRecyclerView()
        val recyclerState = RecyclerState.Base(ScrollMode.SharpUp, false)
        recyclerState.apply(recyclerView)

        assertEquals(1, recyclerView.scrollCalledList.size)
        assertEquals(ScrollMode.SharpUp, recyclerView.scrollCalledList[0])

        assertEquals(1, recyclerView.nestedScrollCalledList.size)
        assertEquals(false, recyclerView.nestedScrollCalledList[0])

        RecyclerState.Disable.apply(recyclerView)

        assertEquals(2, recyclerView.scrollCalledList.size)
        assertEquals(ScrollMode.NoScroll, recyclerView.scrollCalledList[1])

        assertEquals(2, recyclerView.nestedScrollCalledList.size)
        assertEquals(false, recyclerView.nestedScrollCalledList[1])
    }


    private class TestRecyclerView : AbstractView.Recycler {

        val scrollCalledList = mutableListOf<ScrollMode>()
        val nestedScrollCalledList = mutableListOf<Boolean>()

        override fun scroll(scrollMode: ScrollMode) { scrollCalledList.add(scrollMode) }

        override fun nestedScroll(enabled: Boolean) { nestedScrollCalledList.add(enabled) }
    }

}