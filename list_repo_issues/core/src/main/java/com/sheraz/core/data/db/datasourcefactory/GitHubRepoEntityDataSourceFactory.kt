package com.sheraz.core.data.db.datasourcefactory

import androidx.paging.DataSource
import com.sheraz.core.data.db.dao.GitHubRepoEntityDao
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepoEntityDataSourceFactory @Inject constructor(
    private val logger: Logger,
    private val dao: GitHubRepoEntityDao
) : BaseDataSourceFactory<GitHubRepoEntity>(logger) {

    init {
        logger.d(TAG, "init(): ")
    }

    override fun create(): DataSource<Int, GitHubRepoEntity> {

        logger.d(TAG, "create(): repoName = $repoName, likeQuery = $likeQuery")
        return buildDataSource()

    }

    fun setRepoAndQuery(name: String): GitHubRepoEntityDataSourceFactory {
        // appending '%' so we can allow other characters to be before and after the query string
        name.let { this.repoName = it }.also { this.likeQuery = "%${name.replace(' ', '%')}%" }
        logger.d(TAG, "setRepoAndQuery(): repoName = $repoName, likeQuery = $likeQuery")
        create()
        return this
    }

    private fun buildDataSource(): DataSource<Int, GitHubRepoEntity> {

        logger.d(TAG, "buildDataSource(): repoName = $repoName, likeQuery = $likeQuery")
        dataSourceFactory = dao.getAllReposPaged(likeQuery)
        return dataSourceFactory.create().also { dataSource = it }

    }

    companion object {
        private val TAG = GitHubRepoEntityDataSourceFactory::class.java.simpleName
    }
}