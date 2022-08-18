package dev.davlatov.github_clone_app.repositories

import dev.davlatov.github_clone_app.data.remote.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAccessToken(clientID: String, clientSecret: String, code: String) =
        apiService.getAccessToken(clientID, clientSecret, code)
}
