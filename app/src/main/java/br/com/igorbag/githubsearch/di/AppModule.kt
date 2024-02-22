package br.com.igorbag.githubsearch.di

import br.com.igorbag.githubsearch.api.service.interfaces.UserService
import br.com.igorbag.githubsearch.api.service.useCase.UserServiceImpl
import br.com.igorbag.githubsearch.repository.UserRepository
import br.com.igorbag.githubsearch.ui.utils.Constants
import br.com.igorbag.githubsearch.ui.utils.Preferences
import br.com.igorbag.githubsearch.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECTION_TIMEOUT = 30 * 1000

val netWorkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .header("accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }.connectTimeout(
            CONNECTION_TIMEOUT.toLong(),
            TimeUnit.MINUTES
        ).readTimeout(1, TimeUnit.MINUTES).build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}
val databaseModule = module {

    single {
        Preferences(androidContext())
    }
}
val serviceModule = module{
    single {
        get<Retrofit>(Retrofit::class).create(UserService::class.java)
    }
}
val useCase = module {
    single {
        UserServiceImpl(get())
    }

}
val repositoryModule = module{

    single<CoroutineDispatcher> { Dispatchers.Default }

    single {
        UserRepository(get(), get())
    }

}
val viewModelModule = module{

    viewModel {
        UserViewModel(get())
    }

}


val listModules = listOf(netWorkModule, useCase, databaseModule, serviceModule, repositoryModule, viewModelModule)