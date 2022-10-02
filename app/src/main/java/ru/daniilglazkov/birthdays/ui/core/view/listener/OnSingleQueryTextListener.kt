package ru.daniilglazkov.birthdays.ui.core.view.listener

import androidx.appcompat.widget.SearchView

/**
 * @author Danil Glazkov on 24.09.2022, 05:05
 */
abstract class OnSingleQueryTextListener : SearchView.OnQueryTextListener {
    override fun onQueryTextChange(newText: String)= onTextChange(newText).run { true }
    override fun onQueryTextSubmit(query: String) = onTextChange(query).run { true }

    open fun onTextChange(text: String) = Unit
    open fun onTextSubmit(text: String) = Unit
}