package com.sheraz.core.network

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import kotlinx.coroutines.Deferred
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
    fun getRepoIssues(
        @Path("ownerName") ownerName: String,
        @Path("repoName") repoName: String,
        @Query("per_page") pageSize: Int,
        @Query("page") page: Int
    ): Deferred<Response<List<GitHubRepoIssueEntity>>>


    companion object {

        @Volatile
        private var instance: GitHubApiService? = null
        private const val BASE_URL = "https://api.github.com"

        operator fun invoke(context: Context): GitHubApiService = instance ?: synchronized(this) {
            return instance ?: buildApi(context).also { instance = it }
        }

        private fun buildApi(context: Context) =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build().create(GitHubApiService::class.java)

        private fun getOkHttpClient(context: Context) =
            OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 10*1024*1024))
                .addInterceptor(getLoggingInterceptor())
                .build()

        private fun getLoggingInterceptor() = HttpLoggingInterceptor(HttpLogger()).also { it.level = HttpLoggingInterceptor.Level.BODY }

    }

}