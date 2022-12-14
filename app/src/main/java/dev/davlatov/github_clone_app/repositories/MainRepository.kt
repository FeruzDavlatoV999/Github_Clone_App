package dev.davlatov.github_clone_app.repositories

import dev.davlatov.github_clone_app.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUserData(token: String) = apiService.getUserData(/*token*/)
    suspend fun getUserRepositories(token:String, username: String) = apiService.getUserRepositories(/*token*/username)
    suspend fun getUsers(query: String) = apiService.searchUsers(query)
    suspend fun getRepositories(query: String) = apiService.searchRepositories(query)
}