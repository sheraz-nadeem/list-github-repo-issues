package com.sheraz.core.data.db.datasourcefactory

import androidx.paging.DataSource
import com.sheraz.core.utils.Logger

abstract class BaseDataSourceFactory<GENERIC_ENTITY: Any>(
    private val logger: Logger
): DataSource.Factory<Int, GENERIC_ENTITY>() {

    init {
        logger.d(TAG, "init(): ")
    }

    protected var repoName: String = ""
    protected var likeQuery: String = ""
    protected lateinit var dataSourceFactory: DataSource.Factory<Int, GENERIC_ENTITY>
    protected lateinit var dataSource: DataSource<Int, GENERIC_ENTITY>


    fun getLikeQueryForEntity(): String = likeQuery
    fun getDataSourceFactoryForEntity() = dataSourceFactory


    companion object {
        private val TAG = BaseDataSourceFactory::class.java.simpleName
    }
}