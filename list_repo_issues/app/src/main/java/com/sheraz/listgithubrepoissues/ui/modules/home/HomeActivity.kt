package com.sheraz.listgithubrepoissues.ui.modules.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.listgithubrepoissues.BR
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.ActivityHomeBinding
import com.sheraz.listgithubrepoissues.di.Injector
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.modules.adapters.HomeAdapter
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseActivityToolbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_toolbar.*

/**
 * HomeActivity class for our Home view.
 */

class HomeActivity : BaseActivityToolbar<ActivityHomeBinding, HomeViewModel>() {

    private var activityHomeBinding: ActivityHomeBinding? = null
    private lateinit var pagedItmesList: PagedList<GitHubRepoIssueItem>
    private val homeViewModelFactory: HomeViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    private val appRepository: AppRepository
    private val homeAdapter: HomeAdapter

    private val ownerName = "tensorflow"
    private val repoName = "ecosystem"
    private var smoothScrollNeeded = false

    private val pagedListObserver = Observer<PagedList<GitHubRepoIssueItem>> { submitList(it, false) }
    private val loadingStatusObserver = Observer<Boolean> { handleFetchInProgress(it) }
    private val networkErrorObserver = Observer<Exception> { handleNetworkError(it) }

    init {

        logger.d(TAG, "init(): ")
        homeAdapter = Injector.getAppComponent().homeAdapter()
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
        rvGitHubRepoIssuesList.layoutManager = LinearLayoutManager(this)
        rvGitHubRepoIssuesList.adapter = homeAdapter

    }

    override fun subscribeUi() {

        logger.d(TAG, "subscribeUi(): ")

        homeViewModel.pagedListLiveData?.observe(this, pagedListObserver)
        homeViewModel.networkFetchStatusLiveData.observe(this, loadingStatusObserver)
        homeViewModel.networkErrorStatusLiveData.observe(this, networkErrorObserver)
        homeViewModel.loadData(ownerName, repoName)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.clearLocalDbCache -> handleClearCache()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun submitList(pagedList: PagedList<GitHubRepoIssueItem>?, isRefreshing: Boolean) {

        logger.d(TAG, "submitList(): pagedList: ${pagedList?.size}, isRefreshing: $isRefreshing")
        homeAdapter.submitList(pagedList)
        swipeRefreshLayout.isRefreshing = isRefreshing
        pagedItmesList = pagedList!!

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
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.onRefresh(ownerName, repoName) }

    }

    private fun handleClearCache() {

        logger.d(TAG, "handleClearCache(): ")
        homeViewModel.pagedListLiveData?.removeObservers(this)
        homeViewModel.onClearCache()
        homeAdapter.currentList?.dataSource?.invalidate()
        homeViewModel.buildLivePagedList()
        homeViewModel.pagedListLiveData?.observe(this, pagedListObserver)

    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}
