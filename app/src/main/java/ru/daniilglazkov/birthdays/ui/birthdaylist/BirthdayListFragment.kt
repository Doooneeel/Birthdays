package ru.daniilglazkov.birthdays.ui.birthdaylist

import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdaysFragmentBinding
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.BirthdayItemUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.BirthdayListChips
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.BirthdayListAdapter
import ru.daniilglazkov.birthdays.ui.birthdaylist.recyclerstate.RecyclerState
import ru.daniilglazkov.birthdays.ui.core.Debounce
import ru.daniilglazkov.birthdays.ui.core.click.OnSingleClickCallback
import ru.daniilglazkov.birthdays.ui.core.view.listener.SingleOnQueryTextListener
import ru.daniilglazkov.birthdays.ui.main.BaseFragment

/**
 * @author Danil Glazkov on 10.06.2022, 01:09
 */
class BirthdayListFragment : BaseFragment<BirthdaysFragmentBinding, BirthdayListViewModel.Base>(
    inflate = BirthdaysFragmentBinding::inflate,
    viewModelClass = BirthdayListViewModel.Base::class.java,
    debounce = Debounce.MediumDelay()
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchViewClearFocus: () -> Unit = {
            binding.searchView.clearFocus()
        }
        val itemOnClick = OnSingleClickCallback.Base<BirthdayItemUi>(debounce) { birthdayUi ->
            birthdayUi.map(BirthdayItemUi.Mapper.DisplaySheet(childFragmentManager) {
                viewModel.reloadAndFetch()
            })
            searchViewClearFocus.invoke()
        }
        val adapter = BirthdayListAdapter(itemOnClick, searchViewClearFocus)

        binding.appbar.setOnClickListener {
            searchViewClearFocus.invoke()
        }
        binding.newBirthdayImageButton.setOnSingleClick {
            searchViewClearFocus.invoke()
            viewModel.showNewBirthdayDialog()
        }
        binding.menuImageButton.setOnSingleClick {
            searchViewClearFocus.invoke()
            viewModel.showSettingsDialog()
        }
        binding.chipGroup.setOnChipClickListener {
            searchViewClearFocus.invoke()
        }
        binding.searchView.setOnQueryTextListener(SingleOnQueryTextListener { query: String ->
            binding.appbar.setExpanded(true)
            viewModel.changeSearchQuery(query)
            viewModel.fetch()
        })
        binding.recyclerView.adapter = adapter

        viewModel.observe(viewLifecycleOwner) { birthdayItemUiList: BirthdayItemUiList ->
            birthdayItemUiList.apply(adapter)
        }
        viewModel.observeChips(viewLifecycleOwner) { chips: BirthdayListChips ->
            chips.apply(binding.chipGroup)
        }
        viewModel.observeRecyclerState(viewLifecycleOwner) { recyclerState: RecyclerState ->
            recyclerState.apply(binding.recyclerView)
        }
        viewModel.init(savedInstanceState == null)
        viewModel.fetch()
    }
}