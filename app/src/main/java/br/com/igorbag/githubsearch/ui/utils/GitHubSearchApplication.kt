package br.com.igorbag.githubsearch.ui.utils

import android.app.Application
import br.com.igorbag.githubsearch.di.listModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitHubSearchApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@GitHubSearchApplication)
            modules(listModules)
        }
    }

}