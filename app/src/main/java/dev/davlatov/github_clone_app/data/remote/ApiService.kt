package dev.davlatov.github_clone_app.data.remote

import dev.davlatov.github_clone_app.models.AccessToken
import dev.davlatov.github_clone_app.models.Repository
import dev.davlatov.github_clone_app.models.User
import dev.davlatov.github_clone_app.models.comments_response.CommentsResponse
import dev.davlatov.github_clone_app.models.repositories_search_response.RepositoriesResponse
import dev.davlatov.github_clone_app.models.user_repos_response.UserRepositoriesResponseItem
import dev.davlatov.github_clone_app.models.users_search_response.UsersResponse
import dev.davlatov.github_clone_app.utils.Constants
import retrofit2.http.*


interface ApiService {

    companion object {
        const val BASE_URL = Constants.apiURL
        const val IS_TEST_SERVER: Boolean = true
    }

    // get access token
    @POST(Constants.domainURL + "login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): AccessToken

    // get current user' repos
    @GET("user/repos")
    suspend fun getRepositories(): List<Repository>

    // get current user data
    @GET("user")
    suspend fun getUserData(): User

    // get repos by searching
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): RepositoriesResponse

    // get users by searching
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): UsersResponse

    // get user repos' comments
    @GET("repos/{username}/{repo_name}/issues/comments")
    suspend fun getComments(
        @Path("username") username: String,
        @Path("repo_name") repoName: String
    ): CommentsResponse


    // get user repos'
    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String
    ): List<UserRepositoriesResponseItem>
}