package dev.davlatov.github_clone_app.ui.home.details.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.davlatov.github_clone_app.models.user_repos_response.UserRepositoriesResponseItem
import dev.davlatov.github_clone_app.repositories.MainRepository
import dev.davlatov.github_clone_app.ui.profile.ProfileViewModel
import dev.davlatov.github_clone_app.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    companion object {
        private val TAG: String = ProfileViewModel::class.java.simpleName.toString()
    }
    val userRepositories =
        MutableLiveData<Resource<List<UserRepositoriesResponseItem>>>()

    fun getUserRepositories(token: String) {
        viewModelScope.launch {
            userRepositories.postValue(Resource.loading(null))
            try {
                val userData = repository.getUserData(token)
                val repositories = repository.getUserRepositories(token, userData.username)
                userRepositories.postValue(Resource.success(repositories))
            } catch (exception: Exception) {
                userRepositories.postValue(Resource.error("$exception", null))
            }
        }
    }
}

