package ru.daniilglazkov.birthdays.ui.core.view.listener

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Danil Glazkov on 12.11.2022, 16:33
 */
interface OnQueryTextListener : SearchView.OnQueryTextListener {

    abstract class AbstractDebounced(
        private val lifecycle: Lifecycle,
        private val debouncePeriod: Long,
        private val onDebounceQueryTextChange: (String) -> Unit,
    ) : OnQueryTextListener {
        private val debounceQuery: (String) -> Job = {
            lifecycle.coroutineScope.launch {
                delay(debouncePeriod)
                onDebounceQueryTextChange.invoke(it)
            }
        }
        private var searchJob: Job = Job()
        private var isFirstRun = true

        override fun onQueryTextChange(newText: String): Boolean {
            //First call on screen rotation
            if (isFirstRun) {
                isFirstRun = false
                return false
            }
            searchJob.cancel()
            searchJob = debounceQuery.invoke(newText)

            return false
        }
        override fun onQueryTextSubmit(query: String?): Boolean = false
    }

    class SlightDebounce(lifecycle: Lifecycle, onDebounceQueryTextChange: (String) -> Unit) :
        AbstractDebounced(lifecycle, debouncePeriod = 250L, onDebounceQueryTextChange)
}