package com.sheraz.core.network

import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * An interface that is used by [Retrofit] to provide
 * methods for network calls
 */

interface GitHubApiService {

    /**
     * @param ownerName String repository owner name
     * @param repoName String repository name
     * @param pageSize Int count for number of items to include in response
     * @param page Int used for page number
     *
     * @return Returns {@see kotlinx.coroutines.Deferred} value
     * that has a result of type {@see retrofit2.Response}
     */
    @GET("/repos/{ownerName}/{repoName}/issues")
    fun getRepoIssuesAsync(
        @Path("ownerName") ownerName: String,
        @Path("repoName") repoName: String,
        @Query("per_page") pageSize: Int,
        @Query("page") page: Int
    ): Deferred<Response<List<GitHubRepoIssueEntity>>>


    companion object {

        @Volatile
        private var instance: GitHubApiService? = null

        operator fun invoke(retrofit: Retrofit): GitHubApiService = instance ?: synchronized(this) {
            return instance ?: buildApi(retrofit).also { instance = it }
        }

        private fun buildApi(retrofit: Retrofit) = retrofit.create(GitHubApiService::class.java)

    }

}