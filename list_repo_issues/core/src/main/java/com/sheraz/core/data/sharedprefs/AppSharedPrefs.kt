package com.sheraz.core.data.sharedprefs

import android.content.SharedPreferences
import com.sheraz.core.data.sharedprefs.AppSharedPrefs.Companion.DEFAULT_GITHUB_REPO_NAME
import com.sheraz.core.data.sharedprefs.AppSharedPrefs.Companion.DEFAULT_GITHUB_REPO_OWNER
import com.sheraz.core.data.sharedprefs.AppSharedPrefs.Companion.DEFAULT_GITHUB_REPO_SEARCH_QUERY
import com.sheraz.core.data.sharedprefs.AppSharedPrefs.Companion.GITHUB_REPO_SEARCH_QUERY_KEY
import com.sheraz.core.data.sharedprefs.AppSharedPrefs.Companion.SELECTED_GITHUB_REPO_NAME_KEY
import com.sheraz.core.data.sharedprefs.AppSharedPrefs.Companion.SELECTED_GITHUB_REPO_OWNER_KEY
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Extension functions for [SharedPreferences] class
 */

fun SharedPreferences.saveInt(valueKey: String, value: Int) = edit().putInt(valueKey, value).apply()
fun SharedPreferences.saveString(valueKey: String, value: String) = edit().putString(valueKey, value).apply()
fun SharedPreferences.saveBoolean(valueKey: String, value: Boolean) = edit().putBoolean(valueKey, value).apply()
fun SharedPreferences.saveLong(valueKey: String, value: Long) = edit().putLong(valueKey, value).apply()
fun SharedPreferences.saveFloat(valueKey: String, value: Float) = edit().putFloat(valueKey, value).apply()
fun SharedPreferences.clearPrefs() = edit().clear().apply()


/**
 * Extension functions for [AppSharedPrefs] class
 */

fun AppSharedPrefs.getGitHubRepoOwner() = this.get(SELECTED_GITHUB_REPO_OWNER_KEY, DEFAULT_GITHUB_REPO_OWNER)
fun AppSharedPrefs.setGitHubRepoOwner(ownerName: String) = this.set(SELECTED_GITHUB_REPO_OWNER_KEY, ownerName)

fun AppSharedPrefs.getGitHubRepoName() = this.get(SELECTED_GITHUB_REPO_NAME_KEY, DEFAULT_GITHUB_REPO_NAME)
fun AppSharedPrefs.setGitHubRepoName(repoName: String) = this.set(SELECTED_GITHUB_REPO_NAME_KEY, repoName)

fun AppSharedPrefs.getSearchQuery() = this.get(GITHUB_REPO_SEARCH_QUERY_KEY, DEFAULT_GITHUB_REPO_SEARCH_QUERY)
fun AppSharedPrefs.setSearchQuery(query: String) = this.set(GITHUB_REPO_SEARCH_QUERY_KEY, query)

@Singleton
class AppSharedPrefs @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun clearPrefsCache(): Unit = sharedPreferences.clearPrefs()

    fun get(valueKey: String, valueDefault: String): String = sharedPreferences.getString(valueKey, valueDefault)!!

    fun set(valueKey: String, value: String): Unit = sharedPreferences.saveString(valueKey, value)

    fun get(valueKey: String, valueDefault: Int): Int = sharedPreferences.getInt(valueKey, valueDefault)

    fun set(valueKey: String, value: Int): Unit = sharedPreferences.saveInt(valueKey, value)

    fun get(valueKey: String, valueDefault: Boolean): Boolean = sharedPreferences.getBoolean(valueKey, valueDefault)

    fun set(valueKey: String, value: Boolean): Unit = sharedPreferences.saveBoolean(valueKey, value)

    fun get(valueKey: String, valueDefault: Long): Long = sharedPreferences.getLong(valueKey, valueDefault)

    fun set(valueKey: String, value: Long): Unit = sharedPreferences.saveLong(valueKey, value)

    fun get(valueKey: String, valueDefault: Float): Float = sharedPreferences.getFloat(valueKey, valueDefault)

    fun set(valueKey: String, value: Float): Unit = sharedPreferences.saveFloat(valueKey, value)


    companion object {
//        @Volatile
//        private var INSTANCE: AppSharedPrefs? = null
//
//        fun getInstance(sharedPreferences: SharedPreferences): AppSharedPrefs {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: AppSharedPrefs(sharedPreferences).also {
//                    INSTANCE = it
//                }
//            }
//        }

        const val SELECTED_GITHUB_REPO_OWNER_KEY = "selected_github_repo_owner_key"
        const val SELECTED_GITHUB_REPO_NAME_KEY = "selected_github_repo_name_key"
        const val DEFAULT_GITHUB_REPO_OWNER = "tensorflow"
        const val DEFAULT_GITHUB_REPO_NAME = "ecosystem"

        const val GITHUB_REPO_SEARCH_QUERY_KEY = "github_repo_search_query_key"
        const val DEFAULT_GITHUB_REPO_SEARCH_QUERY = "tensorflow"
    }
}
