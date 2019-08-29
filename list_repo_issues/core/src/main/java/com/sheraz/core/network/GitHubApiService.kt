package com.sheraz.core.network

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sheraz.core.network.response.GetGitHubRepoIssuesResponse
import kotlinx.coroutines.Deferred
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    @GET("/repos/tensorflow/ecosystem/issues")
    fun getRepoIssues(
        @Query("PageSize") PageSize: Int,
        @Query("Page") Page: Int
    ): Deferred<Response<GetGitHubRepoIssuesResponse>>


    companion object {

        @Volatile
        private var instance: GitHubApiService? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): GitHubApiService = instance ?: synchronized(LOCK) {
            return instance ?: buildApi(context).also { instance = it }
        }

        private fun buildApi(context: Context) =
            Retrofit.Builder()
                .client(getOkHttpClient(context))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build().create(GitHubApiService::class.java)

        private fun getOkHttpClient(context: Context) =
            OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, 10*1024*1024))
                .addInterceptor(HttpLoggingInterceptor(HttpLogger()))
                .build()


    }

}