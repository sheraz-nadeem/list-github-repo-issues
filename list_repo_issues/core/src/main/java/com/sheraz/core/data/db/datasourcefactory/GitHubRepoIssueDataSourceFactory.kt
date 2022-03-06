package com.sheraz.core.data.db.datasourcefactory

import androidx.paging.DataSource
import com.sheraz.core.data.db.dao.GitHubRepoIssueEntityDao
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity
import com.sheraz.core.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepoIssueDataSourceFactory @Inject constructor(
    private val logger: Logger,
    private val dao: GitHubRepoIssueEntityDao
) : BaseDataSourceFactory<GitHubRepoIssueEntity>(logger) {

    private var ownerName: String = ""

    init {
        logger.d(TAG, "init(): ")
    }

    override fun create(): DataSource<Int, GitHubRepoIssueEntity> {

        logger.d(TAG, "create(): repoName = $repoName, likeQuery = $likeQuery")
        return buildDataSource()

    }

    fun setRepoAndQuery(owner: String, name: String): GitHubRepoIssueDataSourceFactory {
        // appending '%' so we can allow other characters to be before and after the query string
        owner.let { this.ownerName = it }.also { this.likeQuery = "$owner/" }
        name.let { this.repoName = it }.also { this.likeQuery = "%${this.likeQuery}${name.replace(' ', '%')}%" }
        logger.d(TAG, "setRepoAndQuery(): repoName = $repoName, likeQuery = $likeQuery")
        create()
        return this
    }

    fun refresh() {
        dataSource.invalidate()
    }

    private fun buildDataSource(): DataSource<Int, GitHubRepoIssueEntity> {

        logger.d(TAG, "buildDataSource(): repoName = $repoName, likeQuery = $likeQuery")
        dataSourceFactory = dao.getAllRepoIssuesPaged(likeQuery)
        return dataSourceFactory.create().also { dataSource = it }

    }

    companion object {
        private val TAG = GitHubRepoIssueDataSourceFactory::class.java.simpleName
    }
}