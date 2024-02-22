package br.com.igorbag.githubsearch.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igorbag.githubsearch.api.model.response.Repository
import br.com.igorbag.githubsearch.api.model.type.DataState
import br.com.igorbag.githubsearch.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val error: LiveData<String>
        get() = _error

    private val _error = MutableLiveData<String>()

    val appState: LiveData<DataState>
        get() = _appState

    private val _appState = MutableLiveData<DataState>()

    val repositories: LiveData<List<Repository>>
        get() = _repositories

    private val _repositories = MutableLiveData<List<Repository>>()

    fun getRepositories(userName: String){
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = repository.getRepositories(userName)
            Log.e("UserViewModel", "getRepositories: $result")
            result.fold(
                onSuccess = {
                    _repositories.value = it
                    _appState.value = DataState.Success
                },
                onFailure = {
                    it.message?.let { e ->
                        _error.value = e
                    } ?: run {
                        _error.value = "Erro Interno"
                    }
                    _appState.value = DataState.Error
                }
            )
        }
    }

}