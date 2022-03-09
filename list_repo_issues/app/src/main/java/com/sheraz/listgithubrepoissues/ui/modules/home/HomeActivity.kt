package com.sheraz.listgithubrepoissues.ui.modules.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sheraz.core.data.sharedprefs.AppSharedPrefs
import com.sheraz.core.data.sharedprefs.getGitHubRepoName
import com.sheraz.core.data.sharedprefs.getGitHubRepoOwner
import com.sheraz.core.network.worker.UpdateReposWorker
import com.sheraz.listgithubrepoissues.BR
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.ActivityHomeBinding
import com.sheraz.listgithubrepoissues.extensions.findFragmentByTag
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.modules.adapters.HomeAdapter
import com.sheraz.listgithubrepoissues.ui.modules.base.BaseActivityToolbar
import com.sheraz.listgithubrepoissues.ui.modules.home.detail.GoToDetailBottomSheetDialogFragment
import com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo.SearchRepositoryBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.llNoData
import kotlinx.android.synthetic.main.activity_home.rvGitHubRepoIssuesList
import kotlinx.android.synthetic.main.activity_home.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_home.tvRepoFullName
import kotlinx.android.synthetic.main.activity_home.tvRepoOwnerName
import javax.inject.Inject

/**
 * HomeActivity class for our Home view.
 */

@AndroidEntryPoint
class HomeActivity : BaseActivityToolbar<ActivityHomeBinding, HomeViewModel>(),
    SearchRepositoryBottomSheetDialogFragment.OnChooseRepositoryListener {

    private var activityHomeBinding: ActivityHomeBinding? = null
    private var ownerName = ""
    private var repoName = ""
    private var isActivityRecreated = false

    @Inject
    lateinit var appSharedPrefs: AppSharedPrefs

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private val pagedListObserver = Observer<PagedList<GitHubRepoIssueItem>> { submitList(it, false) }
    private val loadingStatusObserver = Observer<Boolean> { handleFetchInProgress(it) }
    private val networkErrorObserver = Observer<Exception> { handleNetworkError(it) }
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.d(TAG, "onCreate(): homeViewModel = $homeViewModel, logger = $logger, homeAdapter = $homeAdapter")

        isActivityRecreated = savedInstanceState != null
        performDataBinding()
        activityHomeBinding = getViewDataBinding()

        initData()
        initUI()
        updateUi()
        setUpListeners()
        scheduleRepoUpdateWorker()
    }

    private fun scheduleRepoUpdateWorker() {
        UpdateReposWorker.enqueue(this)
    }

    override fun onResume() {
        logger.d(TAG, "onResume(): ")
        super.onResume()
        subscribeUi()
    }

    override fun onPause() {
        logger.d(TAG, "onPause(): ")
        super.onPause()

        // We don't really need to do this as we are observing LiveData
        // and LiveData is lifecycle-aware, so as soon as the activity
        // moves to Lifecycle.State#DESTROYED state the observer will
        // be automatically removed.
        unsubscribeUi()
    }

    override fun getLayoutResId() = R.layout.activity_home

    override fun getBindingVariable() = BR.homeViewModel

    override fun getViewModel() = homeViewModel

    override fun initData() {

        ownerName = appSharedPrefs.getGitHubRepoOwner()
        repoName = appSharedPrefs.getGitHubRepoName()
        logger.i(TAG, "initData(): ownerName: $ownerName, repoName: $repoName")

    }

    override fun initUI() {

        logger.d(TAG, "initUI(): ")
        setUpActionBar()
        llNoData.visibility = View.GONE
        rvGitHubRepoIssuesList.layoutManager = LinearLayoutManager(this)
        rvGitHubRepoIssuesList.adapter = homeAdapter

    }

    override fun subscribeUi() {

        logger.d(TAG, "subscribeUi(): ")

        homeViewModel.liveDataRepoIssuesPagedList.observe(this, pagedListObserver)
        homeViewModel.networkFetchStatusLiveData.observe(this, loadingStatusObserver)
        homeViewModel.networkErrorStatusLiveData.observe(this, networkErrorObserver)

    }

    override fun unsubscribeUi() {

        logger.d(TAG, "unsubscribeUi(): ")

        homeViewModel.liveDataRepoIssuesPagedList.removeObservers(this)
        homeViewModel.networkFetchStatusLiveData.removeObservers(this)
        homeViewModel.networkErrorStatusLiveData.removeObservers(this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.clearLocalDbCacheMenuItem -> handleClearCache()
            R.id.searchRepoMenuItem -> handleSearchRepo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUi() {

        logger.d(TAG, "updateUi(): ")
        tvRepoOwnerName.text = ownerName
        tvRepoFullName.text = repoName

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
        snackbar.setAction("Retry") { homeViewModel.loadData() }

        val isEmptyList = homeAdapter.currentList?.isEmpty() ?: true
        if (isEmptyList) {
            llNoData.visibility = View.VISIBLE
        }

    }

    private fun setUpListeners() {

        logger.d(TAG, "setUpListeners(): ")
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.onRefresh() }

        homeAdapter.setListener(View.OnClickListener {
            if (it.tag != null) {
                openGoToDetailBottomSheet(it.tag as GitHubRepoIssueItem)
            }
        })
    }

    private fun handleClearCache() {

        logger.d(TAG, "handleClearCache(): ")
        homeViewModel.onClearCache()
    }

    private fun handleSearchRepo() {

        logger.d(TAG, "handleSearchRepo(): ")
        unsubscribeUi()
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

        var fragment: SearchRepositoryBottomSheetDialogFragment? =
            findFragmentByTag(SearchRepositoryBottomSheetDialogFragment.TAG)

        fragment?.dismiss()

        fragment = SearchRepositoryBottomSheetDialogFragment.newInstance()
        fragment.setOnChooseRepositoryListenerListener(this)
        fragment.show(supportFragmentManager, SearchRepositoryBottomSheetDialogFragment.TAG)

    }

    override fun onChooseRepository() {

        logger.d(TAG, "onChooseRepository(): ")
        subscribeUi()
        homeViewModel.selectRepo(appSharedPrefs.getGitHubRepoName())
        initData()
        updateUi()

    }

    companion object {
        private val TAG = HomeActivity::class.java.simpleName
    }
}
