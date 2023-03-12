package ru.daniilglazkov.birthdays.ui.birthdaylist

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.daniilglazkov.birthdays.databinding.BirthdaysFragmentBinding
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipListUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.ChipUi
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.BirthdayListAdapter
import ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state.RecyclerState
import ru.daniilglazkov.birthdays.ui.core.Debounce
import ru.daniilglazkov.birthdays.ui.core.view.click.OnClickCallback
import ru.daniilglazkov.birthdays.ui.core.view.listener.*
import ru.daniilglazkov.birthdays.ui.core.view.recycler.WrapLinearLayoutManager
import ru.daniilglazkov.birthdays.ui.main.BaseFragment

/**
 * @author Danil Glazkov on 10.06.2022, 01:09
 */
class BirthdayListFragment : BaseFragment<BirthdaysFragmentBinding, BirthdayListViewModel.Base>(
    inflate = BirthdaysFragmentBinding::inflate,
    viewModelClass = BirthdayListViewModel.Base::class.java,
) {
    override val clickDebounce = Debounce.MediumDelay()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showSheetMapper = BirthdayItemUi.Mapper.DisplaySheet(childFragmentManager) {
            viewModel.fetch()
        }

        val itemClickListener = OnClickCallback.Debounced(clickDebounce) { item: BirthdayItemUi ->
            item.map(showSheetMapper)
        }

        val adapter = BirthdayListAdapter(itemClickListener)
        val searchViewClearFocus = binding.searchView::clearFocus

        //disable nested scrolling
        binding.root.let { coordinatorLayout: CoordinatorLayout ->
            coordinatorLayout.setOnTouchListener { _, _ ->
                coordinatorLayout.performClick()
                true
            }
        }

        binding.chipGroup.setOnChipClickListener(OnClickCallback.Base { chip: ChipUi ->
            viewModel.changePosition(chip)
            searchViewClearFocus()
        })

        binding.newBirthdayImageButton.setDebouncedOnClickListener {
            viewModel.showNewBirthdayDialog()
            searchViewClearFocus()
        }

        binding.menuImageButton.setDebouncedOnClickListener {
            viewModel.showSettingsDialog()
            searchViewClearFocus()
        }

        binding.searchView.setOnQueryTextListener(
            OnQueryTextListener.SlightDebounce(lifecycle) { newQuery: String ->
                binding.appbar.setExpanded(true)
                viewModel.changeSearchQuery(newQuery)
                viewModel.fetch()
            }
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = WrapLinearLayoutManager(requireContext())

        binding.recyclerView.addOnScrollListener(
            ScrollStateChangeListener.Touch(onTouch = searchViewClearFocus)
        )

        binding.searchView.setOnQueryTextFocusChangeListener(
            SingleOnFocusChangeListener.OutOfFocus(outOfFocus = searchViewClearFocus)
        )

        viewModel.observeBirthdayList(viewLifecycleOwner) { birthdayItemUiList: BirthdayItemUiList ->
            birthdayItemUiList.apply(adapter)
        }

        viewModel.observeChips(viewLifecycleOwner) { chips: ChipListUi ->
            chips.apply(binding.chipGroup)
        }

        viewModel.observeRecyclerState(viewLifecycleOwner) { recyclerState: RecyclerState ->
            recyclerState.apply(binding.recyclerView)
        }

        viewModel.init(isFirstRun = savedInstanceState == null)
        viewModel.fetch()
    }
}