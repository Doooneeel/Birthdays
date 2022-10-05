package ru.daniilglazkov.birthdays.ui.birthdays.settings

import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdaysSettingsSheetFragmentBinding
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
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
            viewModel.changeAdditionalSettings(
                binding.reverseSwitch.isChecked,
                binding.groupSwitch.isChecked
            )
        }
        val sortModeMap by lazy {
            mapOf(
                binding.byDateRadioButton to SortMode.DATE,
                binding.byNameRadioButton to SortMode.NAME,
                binding.byMonthRadioButton to SortMode.MONTH,
                binding.byAgeRadioButton to SortMode.AGE,
                binding.byYearRadioButton to SortMode.YEAR
            )
        }
        sortModeMap.forEach { (checkView: View, sortMode: SortMode) ->
            val backgroundLayout = checkView.parent as View
            val onClickListener = View.OnClickListener {
                viewModel.changeSortMode(sortMode)
            }
            checkView.setOnClickListener(onClickListener)
            backgroundLayout.setOnClickListener(onClickListener)
        }
        binding.reverseSwitch.setOnClickListener(checkBoxClickListener)
        binding.groupSwitch.setOnClickListener(checkBoxClickListener)

        viewModel.observe(viewLifecycleOwner) { showModeUi ->
            showModeUi.apply(sortModeMap.toMap(), binding.reverseSwitch, binding.groupSwitch)
        }
        viewModel.init(savedInstanceState == null)
    }
}