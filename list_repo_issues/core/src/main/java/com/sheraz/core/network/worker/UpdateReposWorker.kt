package com.sheraz.core.network.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.sharedprefs.getSearchQuery
import com.sheraz.core.utils.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class UpdateReposWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var appRepository: AppRepository

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var appSharedPrefs: AppSharedPrefs

    override suspend fun doWork(): Result {
        logger.d(TAG, "doWork: ")

        val currentNumOfRepositories = appRepository.getTotalNumOfRepositories()
        logger.d(TAG, "doWork: clearing current entries($currentNumOfRepositories) - Step 1")

        appRepository.clearReposCache()

        val numOfRepositoriesAfterClear = appRepository.getTotalNumOfRepositories()
        logger.i(TAG, "doWork: cleared repos list, numOfRepositoriesAfterClear = $numOfRepositoriesAfterClear - Step 2")

        logger.d(TAG, "doWork: initiating fetching repos list... - Step 3")
        appRepository.loadGitHubReposList(appSharedPrefs.getSearchQuery())

        val numOfRepositoriesAfterRefresh = appRepository.getTotalNumOfRepositories()
        logger.i(TAG, "doWork: cleared repos list, numOfRepositoriesAfterRefresh = $numOfRepositoriesAfterRefresh - Step 3")

        return if (numOfRepositoriesAfterRefresh > 0 ) {
            logger.v(TAG, "doWork: SUCCESS")
            Result.success()
        } else {
            logger.e(TAG, "doWork: FAILURE")
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "UpdateReposWorker"

        fun enqueue(context: Context) {
            Log.d(TAG, "enqueue: Scheduling worker...")
            val workManager = WorkManager.getInstance(context)
            workManager.cancelAllWorkByTag(TAG)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workerInstance =
                PeriodicWorkRequestBuilder<UpdateReposWorker>(15, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .addTag(TAG)
                    .build()

            workManager.enqueue(workerInstance)
        }
    }
}