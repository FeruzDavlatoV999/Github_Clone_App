package dev.davlatov.github_clone_app.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.davlatov.github_clone_app.models.User
import dev.davlatov.github_clone_app.models.user_repos_response.UserRepositoriesResponseItem
import dev.davlatov.github_clone_app.models.users_search_response.UsersResponse
import dev.davlatov.github_clone_app.repositories.MainRepository
import dev.davlatov.github_clone_app.utils.Logger
import dev.davlatov.github_clone_app.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    companion object {
        private val TAG: String = ProfileViewModel::class.java.simpleName.toString()
    }

    val userAllData =
        MutableLiveData<Resource<Triple<UsersResponse, List<UserRepositoriesResponseItem>, User>>>()

    fun getUserAllData(token: String) {
        viewModelScope.launch {
            userAllData.postValue(Resource.loading(null))
            try {
                val userData = repository.getUserData(token)
                Logger.e("tag","logger error30")
                val userAll = repository.getUsers(userData.username)
                Logger.e("tag","logger error32")
                val userRepositories = repository.getUserRepositories(token, userData.username)
                Logger.e("tag","logger error34")

                val triple = Triple(userAll, userRepositories, userData)
                userAllData.postValue(Resource.success(triple))
            } catch (exception: Exception) {
                userAllData.postValue(Resource.error("$exception", null))
            }
        }
    }
}


