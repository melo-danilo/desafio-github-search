package br.com.igorbag.githubsearch.api.service.useCase

import br.com.igorbag.githubsearch.api.model.response.Repository
import br.com.igorbag.githubsearch.api.service.interfaces.UserService
import retrofit2.Response

class UserServiceImpl(
    private val userService: UserService
) : UserService {

    override suspend fun getRepositories(userName: String): Response<List<Repository>> {
        return userService.getRepositories(userName)
    }

}