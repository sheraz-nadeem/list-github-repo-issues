package com.sheraz.listgithubrepoissues.ui.modules.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sheraz.core.data.repository.AppRepository
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.listgithubrepoissues.BR
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.ActivityHomeBinding
import com.sheraz.listgithubrepoissues.di.Injector
import com.sheraz.listgithubrepoissues.extensions.bindViewModel
import com.sheraz.listgithubrepoissues.extensions.findFragmentByTag
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.modules.adapters.HomeAdapter
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseActivityToolbar
import com.sheraz.listgithubrepoissues.ui.modules.home.detail.GoToDetailBottomSheetDialogFragment
import com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo.SearchRepositoryBottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_home.*

/**
 * HomeActivity class for our Home view.
 */

class HomeActivity : BaseActivityToolbar<ActivityHomeBinding, HomeViewModel>(), SearchRepositoryBottomSheetDialogFragment.OnChooseRepositoryListener {

    private var activityHomeBinding: ActivityHomeBinding? = null

    private var ownerName = ""
    private var repoName = ""
    private var isActivityRecreated = false

    private val appSharedPrefs: AppSharedPrefs
    private val homeViewModelFactory: HomeViewModelFactory
    private val appRepository: AppRepository
    private val homeAdapter: HomeAdapter

    private val pagedListObserver = Observer<PagedList<GitHubRepoIssueItem>> { submitList(it, false) }
    private val loadingStatusObserver = Observer<Boolean> { handleFetchInProgress(it) }
    private val networkErrorObserver = Observer<Exception> { handleNetworkError(it) }

    init {

        logger.d(TAG, "init(): ")
        appSharedPrefs = Injector.getCoreComponent().sharedPrefs()
        homeAdapter = Injector.getAppComponent().homeAdapter()
        appRepository = Injector.getCoreComponent().appRepository()
        homeViewModelFactory = Injector.getAppComponent().homeViewModelFactory()

    }

    private val homeViewModel by bindViewModel<HomeViewModel>(homeViewModelFactory)


    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)

        isActivityRecreated = savedInstanceState != null
        performDataBinding()
        activityHomeBinding = getViewDataBinding()

        initUI()
        setUpListeners()
        subscribeUi()
    }

    override fun onResume() {
        logger.d(TAG, "onResume(): ")
        super.onResume()
    }

    override fun onPause() {
        logger.d(TAG, "onPause(): ")
        super.onPause()
    }

    override fun getLayoutResId() = R.layout.activity_home

    override fun getBindingVariable() = BR.homeViewModel

    override fun getViewModel() = homeViewModel

    override fun initUI() {

        logger.d(TAG, "initUI(): ")
        setUpActionBar()
        llNoData.visibility = View.GONE
        rvGitHubRepoIssuesList.layoutManager = LinearLayoutManager(this)
        rvGitHubRepoIssuesList.adapter = homeAdapter

    }

    override fun subscribeUi() {

        logger.d(TAG, "subscribeUi(): ")

        homeViewModel.pagedListLiveData?.observe(this, pagedListObserver)
        homeViewModel.networkFetchStatusLiveData.observe(this, loadingStatusObserver)
        homeViewModel.networkErrorStatusLiveData.observe(this, networkErrorObserver)
        if (!isActivityRecreated) loadData()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.clearLocalDbCacheMenuItem -> handleClearCache()
            R.id.searchRepoMenuItem -> handleSearchRepo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {

        ownerName = appSharedPrefs.get(AppSharedPrefs.SELECTED_GITHUB_REPO_OWNER_KEY, AppSharedPrefs.DEFAULT_GITHUB_REPO_OWNER) ?: AppSharedPrefs.DEFAULT_GITHUB_REPO_OWNER
        repoName = appSharedPrefs.get(AppSharedPrefs.SELECTED_GITHUB_REPO_NAME_KEY, AppSharedPrefs.DEFAULT_GITHUB_REPO_NAME) ?: AppSharedPrefs.DEFAULT_GITHUB_REPO_NAME
        logger.i(TAG, "loadData(): ownerName: $ownerName, repoName: $repoName")

        homeViewModel.loadData(ownerName, repoName)

    }

    private fun submitList(pagedList: PagedList<GitHubRepoIssueItem>?, isRefreshing: Boolean) {

        logger.d(TAG, "submitList(): pagedList: ${pagedList?.size}, isRefreshing: $isRefreshing")
        homeAdapter.submitList(pagedList)
        swipeRefreshLayout.isRefreshing = isRefreshing

        val pagedListSize: Int = pagedList?.size ?: 0
        if (pagedListSize > 0) {
            llNoData.visibility = View.GONE
        }

    }

    private fun handleFetchInProgress(isFetchInProgress: Boolean) {

        logger.d(TAG, "handleFetchInProgress(): isFetchInProgress: $isFetchInProgress")
        homeViewModel.setIsLoading(isFetchInProgress)
        swipeRefreshLayout.isRefreshing = false

        if (isFetchInProgress) llNoData.visibility = View.GONE

    }

    private fun handleNetworkError(exception: Exception) {

        logger.e(TAG, "handleNetworkError(): exception: $exception")
        swipeRefreshLayout.isRefreshing = false
        val snackbar = Snackbar.make(activityHomeBinding?.root!!, exception.message.toString(), Snackbar.LENGTH_LONG)
        snackbar.setAction("Retry") { loadData() }

        if (homeAdapter.currentList?.isEmpty()!!) {
            llNoData.visibility = View.VISIBLE
        }

    }

    private fun setUpListeners() {

        logger.d(TAG, "setUpListeners(): ")
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.onRefresh(ownerName, repoName) }

        homeAdapter.setListener(View.OnClickListener {
            if (it.tag != null) {
                openGoToDetailBottomSheet(it.tag as GitHubRepoIssueItem)
            }
        })
    }

    private fun handleClearCache() {

        logger.d(TAG, "handleClearCache(): ")
        homeViewModel.pagedListLiveData?.removeObservers(this)
        homeViewModel.onClearCache()
        homeAdapter.currentList?.dataSource?.invalidate()
        homeViewModel.buildLivePagedList()
        homeViewModel.pagedListLiveData?.observe(this, pagedListObserver)

    }

    private fun handleSearchRepo() {

        logger.d(TAG, "handleSearchRepo(): ")
        homeViewModel.networkFetchStatusLiveData.removeObservers(this)
        homeViewModel.networkErrorStatusLiveData.removeObservers(this)
        openSearchRepoBottomSheet()

    }

    private fun openGoToDetailBottomSheet(gitHubRepoIssueItem: GitHubRepoIssueItem) {

        logger.d(TAG, "openGoToDetailBottomSheet(): gitHubRepoIssueItem: $gitHubRepoIssueItem")

        var fragment: GoToDetailBottomSheetDialogFragment? = findFragmentByTag(GoToDetailBottomSheetDialogFragment.TAG)

        fragment?.dismiss()

        fragment = GoToDetailBottomSheetDialogFragment.newInstance(gitHubRepoIssueItem)
        fragment.show(supportFragmentManager, GoToDetailBottomSheetDialogFragment.TAG)

    }

    private fun openSearchRepoBottomSheet() {

        logger.d(TAG, "openSearchRepoBottomSheet(): ")

        var fragment: SearchRepositoryBottomSheetDialogFragment? = findFragmentByTag(SearchRepositoryBottomSheetDialogFragment.TAG)

        fragment?.dismiss()

        fragment = SearchRepositoryBottomSheetDialogFragment.newInstance()
        fragment.setOnChooseRepositoryListenerListener(this)
        fragment.show(supportFragmentManager, SearchRepositoryBottomSheetDialogFragment.TAG)

    }

    override fun onChooseRepository() {

        logger.d(TAG, "onChooseRepository(): ")

        handleClearCache()
        homeViewModel.networkFetchStatusLiveData.observe(this, loadingStatusObserver)
        homeViewModel.networkErrorStatusLiveData.observe(this, networkErrorObserver)

        loadData()

    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}
