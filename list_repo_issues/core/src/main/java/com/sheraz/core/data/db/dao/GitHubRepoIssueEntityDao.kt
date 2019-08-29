package com.sheraz.core.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

@Dao
interface GitHubRepoIssueEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gitHubRepoEntity: GitHubRepoIssueEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(gitHubRepoEntityList: List<GitHubRepoIssueEntity>)

    @Query("SELECT * FROM github_repo_issue")
    fun getAllRepoIssues(): LiveData<List<GitHubRepoIssueEntity>>

    @Query("DELETE FROM github_repo_issue")
    fun deleteAll(): Int

    @Query("SELECT COUNT(*) FROM github_repo_issue")
    fun getNumOfRows(): Int
}