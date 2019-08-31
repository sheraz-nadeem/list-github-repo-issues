package com.sheraz.core.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

/**
 * Our Room Database class with [GitHubRepoIssueEntity]
 */
@Database(
    entities = [GitHubRepoIssueEntity::class],
    version = 1,
    exportSchema = false // Just to get rid of the warning generated at build time
)

abstract class GitHubRepoDatabase : RoomDatabase() {


    /**********
     *  All Dao Methods
     **********/

    abstract fun gitHubRepoIssueEntityDao(): GitHubRepoIssueEntityDao

    companion object {
        @Volatile
        private var instance: GitHubRepoDatabase? = null

        // Below invoke function, implemented as an "operator function" which
        // allows us to get instance like => "GitHubRepoDatabase()"
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GitHubRepoDatabase::class.java, "github_repo_db"
            ).build()

    }

}