package dev.davlatov.github_clone_app.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.davlatov.github_clone_app.models.AccessToken
import dev.davlatov.github_clone_app.repositories.AuthRepository
import dev.davlatov.github_clone_app.utils.Constants.clientID
import dev.davlatov.github_clone_app.utils.Constants.clientSecret
import dev.davlatov.github_clone_app.utils.Logger
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    companion object {
        private val TAG: String = LoginViewModel::class.java.simpleName.toString()
    }

    val accessToken = MutableLiveData<AccessToken>()

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            try {
                accessToken.value = repository.getAccessToken(clientID, clientSecret, code)
                Logger.d(TAG, "AccessToken: ${accessToken.value?.accessToken}")
            } catch (exception: Exception) {
                Logger.e(TAG, "getAccessToken: error $exception")
            }
        }
    }
}
