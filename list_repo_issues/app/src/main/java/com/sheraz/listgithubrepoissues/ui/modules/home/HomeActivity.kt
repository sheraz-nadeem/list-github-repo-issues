package com.sheraz.listgithubrepoissues.ui.modules.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.BR
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.ActivityHomeBinding
import com.sheraz.listgithubrepoissues.di.Injector
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseActivityToolbar
import kotlinx.android.synthetic.main.activity_home.*

/**
 * HomeActivity class for our Home view.
 */

class HomeActivity : BaseActivityToolbar<ActivityHomeBinding, HomeViewModel>() {

    private var activityHomeBinding: ActivityHomeBinding? = null
    private val homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    private val appRepository: AppRepository

    private val ownerName = "tensorflow"
    private val repoName = "ecosystem"

    init {

        logger.d(TAG, "init(): ")
        appRepository = Injector.getCoreComponent().appRepository()
        homeViewModelFactory = Injector.getAppComponent().homeViewModelFactory()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)

        homeViewModel = ViewModelProviders.of(this, homeViewModelFactory).get(HomeViewModel::class.java)

        performDataBinding()
        activityHomeBinding = getViewDataBinding()

        initUI()
        setUpListeners()
        subscribeUi()
    }

    override fun getLayoutResId() = R.layout.activity_home

    override fun getBindingVariable() = BR.homeViewModel

    override fun getViewModel() = homeViewModel

    override fun initUI() {

        logger.d(TAG, "initUI(): ")
        setUpActionBar()
        fab.hide()

    }

    override fun subscribeUi() {

        logger.d(TAG, "subscribeUi(): ")

        homeViewModel.getGitHubRepoIssueItemsList().observe(this, Observer { pagedList ->
            logger.i(TAG, "pagedList.Observer(): pagedList.size: ${pagedList?.size}")
        })

        homeViewModel.getLoadingLiveData().observe(this, Observer { isFetchInProgress ->
            logger.d(TAG, "loading.Observer(): isFetchInProgress: $isFetchInProgress")
            handleFetchInProgress(isFetchInProgress)
        })

        homeViewModel.getNetworkErrorLiveData().observe(this, Observer { exception ->
            logger.d(TAG, "networkError.Observer(): exception: $exception")
            handleNetworkError(exception)
        })

        homeViewModel.loadData(ownerName, repoName)

    }

    private fun handleFetchInProgress(isFetchInProgress: Boolean) {

        logger.d(TAG, "handleFetchInProgress(): isFetchInProgress: $isFetchInProgress")
        homeViewModel.setIsLoading(isFetchInProgress)
        swipeRefreshLayout.isRefreshing = false

    }

    private fun handleNetworkError(exception: Exception) {

        logger.d(TAG, "handleNetworkError(): exception: $exception")
        swipeRefreshLayout.isRefreshing = false
        Snackbar.make(activityHomeBinding?.root!!, exception.message.toString(), Snackbar.LENGTH_LONG).show()

    }

    private fun setUpListeners() {

        logger.d(TAG, "setUpListeners(): ")
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.onRefresh() }

    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}
