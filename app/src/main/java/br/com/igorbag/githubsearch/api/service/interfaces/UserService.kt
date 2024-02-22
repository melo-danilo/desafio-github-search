package br.com.igorbag.githubsearch.api.service.interfaces

import br.com.igorbag.githubsearch.api.model.response.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{userName}/repos")
    suspend fun getRepositories(
        @Path("userName") userName: String
    ): Response<List<Repository>>

}