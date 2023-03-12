package ru.daniilglazkov.birthdays.ui.settings

import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdaysSettingsSheetFragmentBinding
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment

/**
 * @author Danil Glazkov on 21.07.2022, 22:31
 */
class SettingsSheetFragment :
    BaseSheetFragment<BirthdaysSettingsSheetFragmentBinding, SettingsViewModel.Base>(
        inflate = BirthdaysSettingsSheetFragmentBinding::inflate,
        viewModelClass = SettingsViewModel.Base::class.java
    )
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val checkBoxClickListener = View.OnClickListener {
            viewModel.changeSettings(
                binding.reverseSwitch.isChecked,
                binding.groupSwitch.isChecked
            )
        }

        binding.reverseSwitch.setOnClickListener(checkBoxClickListener)
        binding.groupSwitch.setOnClickListener(checkBoxClickListener)

        binding.sortModeGridLayout.setOnSortModeRadioButtonClick { sortMode: SortMode ->
            viewModel.changeSortMode(sortMode)
        }

        viewModel.observeSettings(viewLifecycleOwner) { settings: SettingsUi ->
            settings.apply(binding.reverseSwitch, binding.groupSwitch)
            settings.applySortMode(binding.sortModeGridLayout)
        }

        viewModel.fetch()
    }

    override fun onPause() {
        viewModel.complete()

        super.onPause()
    }
}