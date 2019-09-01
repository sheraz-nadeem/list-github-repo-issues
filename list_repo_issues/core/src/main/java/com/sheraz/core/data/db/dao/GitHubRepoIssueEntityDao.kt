package com.sheraz.core.data.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

/**
 * An Interface that is used to generate an implementation
 * that provides convenience methods to perform Room database
 * operations on `github_repo_issue` table.
 */

@Dao
interface GitHubRepoIssueEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gitHubRepoEntity: GitHubRepoIssueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(gitHubRepoEntityList: List<GitHubRepoIssueEntity>)

    @Query("SELECT * FROM github_repo_issue")
    fun getAllRepoIssuesPaged(): DataSource.Factory<Int, GitHubRepoIssueEntity>

    @Query("SELECT * FROM github_repo_issue")
    fun getAllRepoIssues(): LiveData<List<GitHubRepoIssueEntity>>

    @Query("SELECT * FROM github_repo_issue WHERE state = 'open'")
    fun getOpenIssuesInRepo(): LiveData<List<GitHubRepoIssueEntity>>

    @Query("SELECT * FROM github_repo_issue WHERE state = 'closed'")
    fun getClosedIssuesInRepo(): LiveData<List<GitHubRepoIssueEntity>>

    @Query("DELETE FROM github_repo_issue")
    fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM github_repo_issue")
    fun getNumOfRows(): Int
}