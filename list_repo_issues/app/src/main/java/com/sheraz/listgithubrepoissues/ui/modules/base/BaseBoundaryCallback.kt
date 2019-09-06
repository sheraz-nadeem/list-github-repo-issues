package com.sheraz.listgithubrepoissues.ui.modules.base

import android.os.Parcelable
import androidx.paging.PagedList
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.utils.Logger


abstract class BaseBoundaryCallback<GENERIC_BOUNDARY_CALLBACK_PARCELABLE: Parcelable>(
    private val logger: Logger,
    private val appRepository: AppRepository
) : PagedList.BoundaryCallback<GENERIC_BOUNDARY_CALLBACK_PARCELABLE>() {

    /**
     * Abstract method that must be implemented by child classes
     * to perform tasks that they need to perform when needed
     */
    abstract fun requestData(isLastItem: Boolean)


    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() = requestData(false)

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: GENERIC_BOUNDARY_CALLBACK_PARCELABLE) {
        val noMoreItemsAvailable = appRepository.noMoreItemsAvailable.value
        logger.d(TAG, "onItemAtEndLoaded(): noMoreItemsAvailable = ${noMoreItemsAvailable}, itemAtEnd = $itemAtEnd")
        when (noMoreItemsAvailable) {
            true -> return
            false -> requestData(true)
        }
    }


    companion object {
        private val TAG = BaseBoundaryCallback::class.java.simpleName
    }

}