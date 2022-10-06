package ru.daniilglazkov.birthdays.ui.birthdays.list

import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdaysFragmentBinding
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.Debounce
import ru.daniilglazkov.birthdays.ui.core.click.OnSingleClickCallback
import ru.daniilglazkov.birthdays.ui.core.view.listener.SingleOnQueryTextListener
import ru.daniilglazkov.birthdays.ui.main.BaseFragment

/**
 * @author Danil Glazkov on 10.06.2022, 01:09
 */
class BirthdaysFragment : BaseFragment<BirthdaysFragmentBinding, BirthdaysViewModel.Base>(
    inflate = BirthdaysFragmentBinding::inflate,
    viewModelClass = BirthdaysViewModel.Base::class.java,
    debounce = Debounce.MediumDelay()
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchViewClearFocus: () -> Unit = {
            binding.searchView.clearFocus()
        }
        val itemOnClick = OnSingleClickCallback.Base<BirthdayUi>(debounce) { birthdayUi ->
            birthdayUi.map(BirthdayUi.Mapper.DisplaySheet(childFragmentManager) {
                viewModel.fetch()
            })
            searchViewClearFocus.invoke()
        }
        val adapter = BirthdaysAdapter(itemOnClick, searchViewClearFocus)

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
        binding.searchView.setOnQueryTextListener(SingleOnQueryTextListener { newText ->
            viewModel.changeSearchQuery(newText)
            viewModel.fetch()
        })
        binding.recyclerView.adapter = adapter

        viewModel.observe(viewLifecycleOwner) { birthdaysUi ->
            birthdaysUi.apply(adapter)
        }
        viewModel.observeChips(viewLifecycleOwner) { chips ->
            chips.apply(binding.chipGroup)
        }
        viewModel.observeRecyclerState(viewLifecycleOwner) { recyclerState ->
            recyclerState.apply(binding.recyclerView)
        }
        viewModel.init(savedInstanceState == null)
        viewModel.fetch()
    }
}