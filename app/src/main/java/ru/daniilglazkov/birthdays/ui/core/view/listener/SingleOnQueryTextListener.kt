package ru.daniilglazkov.birthdays.ui.core.view.listener

import androidx.appcompat.widget.SearchView

/**
 * @author Danil Glazkov on 24.09.2022, 05:05
 */
fun interface SingleOnQueryTextListener : SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String): Boolean {
        return onTextChange(newText).run { true }
    }
    override fun onQueryTextSubmit(query: String): Boolean = true

    fun onTextChange(newText: String)
}