package com.sheraz.core.network

import com.sheraz.core.network.response.GetGitHubRepoIssuesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    @GET("/repos/tensorflow/ecosystem/issues")
    fun getRepoIssues(
        @Query("PageSize") PageSize: Int,
        @Query("Page") Page: Int
    ): Deferred<Response<GetGitHubRepoIssuesResponse>>

}