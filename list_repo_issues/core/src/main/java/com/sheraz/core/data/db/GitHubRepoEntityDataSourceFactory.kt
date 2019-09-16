package com.sheraz.core.data.db

import androidx.paging.DataSource
import com.sheraz.core.data.db.dao.GitHubRepoEntityDao
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.utils.Logger

class GitHubRepoEntityDataSourceFactory(
    private val logger: Logger,
    private val dao: GitHubRepoEntityDao
): DataSource.Factory<Int, GitHubRepoEntity>() {

    private var query: String = ""
    private lateinit var dataSourceFactory: DataSource.Factory<Int, GitHubRepoEntity>
    private lateinit var dataSource: DataSource<Int, GitHubRepoEntity>

    init {
        logger.d(TAG, "init(): ")
    }

    fun setRepoName(name: String): GitHubRepoEntityDataSourceFactory {
        // appending '%' so we can allow other characters to be before and after the query string
        this.query = "%${name.replace(' ', '%')}%"
        logger.d(TAG, "setRepoName(): name = $name, query = $query")
        create()
        return this
    }

    fun getQuery(): String = query
    fun getDataSourceFactory() = dataSourceFactory

    override fun create(): DataSource<Int, GitHubRepoEntity> {


        logger.d(TAG, "create(): query = $query")
        dataSourceFactory = dao.getAllReposPaged(query)
        dataSource = dataSourceFactory.create()
        return dataSource

    }

    companion object {
        private val TAG = GitHubRepoEntityDataSourceFactory::class.java.simpleName
    }
}