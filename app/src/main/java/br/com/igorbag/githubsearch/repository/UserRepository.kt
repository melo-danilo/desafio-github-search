package br.com.igorbag.githubsearch.repository

import br.com.igorbag.githubsearch.api.model.response.Repository
import br.com.igorbag.githubsearch.api.service.useCase.UserServiceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository(
    private val service: UserServiceImpl,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getRepositories(userName: String): Result<List<Repository>?> =
        withContext(dispatcher){
            try {
                val response = service.getRepositories(userName)
                when {

                    response.isSuccessful -> {
                        Result.success(response.body())
                    }

                    else -> {
                        Result.failure(Throwable(response.message()))
                    }
                }
            }catch (e: Exception){
                Result.failure(Throwable(e.message))
            }
        }

}