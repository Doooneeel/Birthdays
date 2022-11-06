package ru.daniilglazkov.birthdays.ui.birthday

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdaySheetFragmentBinding
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.view.CustomToast
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment

/**
 * @author Danil Glazkov on 10.06.2022, 21:48
 */
class BirthdaySheetFragment(
    private val birthdayId: Int = -1,
) : BaseSheetFragment<BirthdaySheetFragmentBinding, BirthdayViewModel.Base>(
    inflate = BirthdaySheetFragmentBinding::inflate,
    viewModelClass = BirthdayViewModel.Base::class.java
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteButton.setOnToggleListener { newState: Boolean ->
            viewModel.changeDeleteState(newState.not())
        }
        viewModel.observe(viewLifecycleOwner) { birthdayUi: BirthdayUi ->
            birthdayUi.apply(
                binding.nameTextView,
                binding.birthdayTextView,
                binding.birthdateTextView,
                binding.turnsYearsOldTextView,
                Pair(binding.daysLeft, binding.daysToBirthdayTextView)
            )
        }
        viewModel.observeBirthdayZodiacs(viewLifecycleOwner) { zodiacsUi: BirthdayZodiacsUi ->
            zodiacsUi.apply(binding.zodiacTextView, binding.zodiacImageView)
            zodiacsUi.applyChinese(binding.chineseZodiacTextView)
        }
        viewModel.observeError(viewLifecycleOwner) { error: ErrorMessage ->
            error.apply(CustomToast(requireContext()))
        }
        viewModel.observeDeleteState(viewLifecycleOwner) { deleteState: Boolean ->
            binding.deleteButton.map(deleteState.not())
        }
        viewModel.init(savedInstanceState == null, birthdayId)
        viewModel.fetch()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        viewModel.complete()
    }
}