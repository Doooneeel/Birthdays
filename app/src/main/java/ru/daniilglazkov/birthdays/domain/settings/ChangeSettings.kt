package ru.daniilglazkov.birthdays.domain.settings

/**
 * @author Danil Glazkov on 26.02.2023, 10:16
 */
interface ChangeSettings {

    suspend fun change(action: (SettingsDomain) -> SettingsDomain): SettingsDomain

    fun changedValue(): SettingsDomain


    class Base : ChangeSettings {
        private var value: SettingsDomain = SettingsDomain.Default

        override suspend fun change(action: (SettingsDomain) -> SettingsDomain): SettingsDomain {
            val changedSettings: SettingsDomain = action.invoke(value)
            value = changedSettings

            return changedSettings
        }

        override fun changedValue(): SettingsDomain = value
    }
}