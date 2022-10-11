package ru.daniilglazkov.birthdays.domain.showmode

import ru.daniilglazkov.birthdays.domain.core.HandleRepositoryResponse

/**
 * @author Danil Glazkov on 10.10.2022, 00:29
 */
interface HandleShowModeRepositoryResponse : HandleRepositoryResponse<ShowModeDomain> {

    class Base : HandleRepositoryResponse.Abstract<ShowModeDomain>(
        ShowModeDomain.Default
    ) , HandleShowModeRepositoryResponse
}