package ru.daniilglazkov.birthdays.ui.birthday

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import ru.daniilglazkov.birthdays.databinding.BirthdaySheetFragmentBinding
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.view.text.CustomToast
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacsUi

/**
 * @author Danil Glazkov on 10.06.2022, 21:48
 */
class BirthdaySheetFragment : BaseSheetFragment<BirthdaySheetFragmentBinding, BirthdayViewModel.Base>(
    inflate = BirthdaySheetFragmentBinding::inflate,
    viewModelClass = BirthdayViewModel.Base::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id: Int = arguments?.getInt(ID_KEY) ?: -1

        viewModel.init(isFirstRun = savedInstanceState == null, id)
        viewModel.fetch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteButton.setOnToggleListener { newState: Boolean ->
            viewModel.changeDeleteState(!newState)
        }

        viewModel.observeBirthday(viewLifecycleOwner) { birthdayUi: BirthdayUi ->
            birthdayUi.apply(
                binding.nameTextView,
                binding.birthdayTextView,
                binding.birthdateTextView,
                binding.turnsYearsOldTextView,
                DaysLeftView(
                    binding.daysLeftTextView,
                    binding.daysToBirthdayTextView
                )
            )
        }

        viewModel.observeZodiacs(viewLifecycleOwner) { zodiacsUi: ZodiacsUi ->
            zodiacsUi.applyGreek(binding.zodiacTextView, binding.zodiacImageView)
            zodiacsUi.applyChinese(binding.chineseZodiacTextView)
        }

        viewModel.observeDeleteState(viewLifecycleOwner) { deleteState: Boolean ->
            binding.deleteButton.map(!deleteState)
        }

        viewModel.observeError(viewLifecycleOwner) { error: ErrorMessage ->
            error.apply(CustomToast(requireContext()))
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        viewModel.complete()
    }

    companion object {
        private const val ID_KEY = "birthdayIdKey"

        fun newInstance(id: Int) = BirthdaySheetFragment().apply {
            arguments = bundleOf(Pair(ID_KEY, id))
        }
    }
}