package com.sheraz.listgithubrepoissues.ui.modules.home.searchrepo

import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.sheraz.core.data.sharedprefs.*
import com.sheraz.listgithubrepoissues.BR
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.FragmentSearchRepositoryBottomSheetBinding
import com.sheraz.listgithubrepoissues.di.Injector
import com.sheraz.listgithubrepoissues.extensions.bindViewModel
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem
import com.sheraz.listgithubrepoissues.ui.modules.adapters.SearchRepositoryAdapter
import com.sheraz.listgithubrepoissues.utils.hideKeyboard
import com.sheraz.listgithubrepoissues.utils.setWhiteNavigationBar
import com.sheraz.listgithubrepoissues.utils.showKeyboard
import kotlinx.android.synthetic.main.fragment_search_repository_bottom_sheet.*


class SearchRepositoryBottomSheetDialogFragment: BottomSheetDialogFragment(), DialogInterface.OnShowListener {

    private lateinit var fragmentSearchRepositoryBottomSheetBinding: FragmentSearchRepositoryBottomSheetBinding

    private var ownerName = ""
    private var repoName = ""

    private val logger = Injector.getCoreComponent().logger()
    private val appSharedPrefs: AppSharedPrefs
    private val searchRepositoryAdapter: SearchRepositoryAdapter
    private val searchRepoViewModelFactory: SearchRepoViewModelFactory

    private val pagedListObserver = Observer<PagedList<GitHubRepoItem>> { submitList(it, false) }
    private val loadingStatusObserver = Observer<Boolean> { handleFetchInProgress(it) }
    private val networkErrorObserver = Observer<Exception> { handleNetworkError(it) }

    // Interfaces
    private var onChooseRepositoryListener: OnChooseRepositoryListener? = null

    init {
        logger.d(TAG, "init(): ")

        appSharedPrefs = Injector.getCoreComponent().sharedPrefs()
        searchRepositoryAdapter = Injector.getAppComponent().searchRepoAdapter()
        searchRepoViewModelFactory = Injector.getAppComponent().searchRepoViewModelFactory()
    }

    private val searchRepoViewModel by bindViewModel<SearchRepoViewModel>(searchRepoViewModelFactory)


    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate(): ")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        logger.d(TAG, "onCreateDialog(): ")
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog)
        }

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        logger.d(TAG, "onCreateView(): ")
        fragmentSearchRepositoryBottomSheetBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_search_repository_bottom_sheet, container, false)

        fragmentSearchRepositoryBottomSheetBinding.setVariable(BR.searchRepoViewModel, searchRepoViewModel)
        fragmentSearchRepositoryBottomSheetBinding.executePendingBindings()

        return fragmentSearchRepositoryBottomSheetBinding.root

    }

    override fun onShow(dialog: DialogInterface) {
        logger.d(TAG, "onShow(): ")
        setBottomSheetDialogHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        logger.d(TAG, "onViewCreated(): ")
        super.onViewCreated(view, savedInstanceState)

        initData()
        initUI()
        setUpListeners()

    }

    private fun initData() {

        ownerName = appSharedPrefs.getGitHubRepoOwner()
        repoName = appSharedPrefs.getGitHubRepoName()
        logger.d(TAG, "initData(): ownerName: $ownerName, repoName: $repoName")

    }

    private fun initUI() {

        logger.d(TAG, "initUI(): ")

        flHeaderTextContainer.visibility = View.VISIBLE
        llSearchContainer.visibility = View.GONE

        rvGitHubRepo.layoutManager = LinearLayoutManager(context)
        rvGitHubRepo.adapter = searchRepositoryAdapter

    }

    private fun setUpListeners() {
        logger.d(TAG, "setUpListeners(): ")

        ibSearchRepoAction.setOnClickListener {
            logger.d(TAG, "ibSearchDevicesAction.onClick(): ")
            llSearchContainer.visibility = View.VISIBLE
            flHeaderTextContainer.visibility = View.GONE
            etSearchRepo.setText(
                appSharedPrefs.get(AppSharedPrefs.GITHUB_REPO_SEARCH_QUERY_KEY,
                    AppSharedPrefs.DEFAULT_GITHUB_REPO_SEARCH_QUERY))
            showKeyboard(context!!, etSearchRepo)
        }

        ibSearchBackAction.setOnClickListener {
            logger.d(TAG, "ibSearchBackAction.OnClick(): ")
            etSearchRepo.setText("")
            llSearchContainer.visibility = View.GONE
            flHeaderTextContainer.visibility = View.VISIBLE
            hideKeyboard(context!!, etSearchRepo)
        }

        etSearchRepo.setOnEditorActionListener { v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){

                if (v.text.toString().trim().isNotEmpty()) {
                    handleClearSearchCache()

                    val searchQuery = v.text.toString().toLowerCase().trim().also { appSharedPrefs.setSearchQuery(it) }
                    logger.i(TAG, "etSearchRepo.onActionDone(): searchQuery: $searchQuery")
//                    searchRepoViewModel.search()
                }

            }
            false
        }

        searchRepositoryAdapter.setListener(View.OnClickListener {
            if (it.tag != null) {

                val gitHubRepoItem = it.tag as GitHubRepoItem
                val ownerName = gitHubRepoItem.ownerLogin ?: ""
                val repoName = gitHubRepoItem.name
                logger.d(TAG, "itemSelected(): ownerName: $ownerName, repoName: $repoName")

                searchRepoViewModel.onClearCache()

                appSharedPrefs.setGitHubRepoOwner(ownerName)
                appSharedPrefs.setGitHubRepoName(repoName)

                if (onChooseRepositoryListener != null) {
                    onChooseRepositoryListener!!.onChooseRepository()
                }

                dismiss()
            }
        })
    }

    private fun subscribeUi() {

        logger.d(TAG, "subscribeUi(): ")

        searchRepoViewModel.getLiveDataPagedList().observe(this, pagedListObserver)
        searchRepoViewModel.networkFetchStatusLiveData.observe(this, loadingStatusObserver)
        searchRepoViewModel.networkErrorStatusLiveData.observe(this, networkErrorObserver)

    }

    private fun unsubscribeUi() {

        logger.d(TAG, "unsubscribeUi(): ")

        searchRepoViewModel.getLiveDataPagedList().removeObservers(this)
        searchRepoViewModel.networkFetchStatusLiveData.removeObservers(this)
        searchRepoViewModel.networkErrorStatusLiveData.removeObservers(this)

    }

    private fun submitList(pagedList: PagedList<GitHubRepoItem>?, isRefreshing: Boolean) {

        logger.d(TAG, "submitList(): pagedList: ${pagedList?.size}, isRefreshing: $isRefreshing")
        searchRepositoryAdapter.submitList(pagedList)
    }

    private fun handleFetchInProgress(isFetchInProgress: Boolean) {
        logger.d(TAG, "handleFetchInProgress(): isFetchInProgress: $isFetchInProgress")
        searchRepoViewModel.setIsLoading(isFetchInProgress)
    }

    private fun handleNetworkError(exception: Exception) {
        logger.e(TAG, "handleNetworkError(): exception: $exception")
        Snackbar.make(fragmentSearchRepositoryBottomSheetBinding.root, exception.message.toString(), Snackbar.LENGTH_LONG).show()
    }

    private fun handleClearSearchCache() {

        logger.d(TAG, "handleClearSearchCache(): ")
        searchRepoViewModel.onClearReposCache()
        searchRepositoryAdapter.currentList?.dataSource?.invalidate()
        searchRepoViewModel.buildLivePagedList()

    }

    override fun onResume() {
        logger.d(TAG, "onResume(): ")
        super.onResume()
        subscribeUi()
    }

    override fun onPause() {
        logger.d(TAG, "onPause(): ")
        super.onPause()
        unsubscribeUi()
    }

    override fun onDestroy() {
        logger.d(TAG, "onDestroy(): ")
        super.onDestroy()
    }

    private fun setBottomSheetDialogHeight() {
        logger.d(TAG, "setBottomSheetDialogHeight(): ")
        llSelectRepoBottomSheetRoot.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */

    interface OnChooseRepositoryListener {
        fun onChooseRepository()
    }

    fun setOnChooseRepositoryListenerListener(listener: OnChooseRepositoryListener) {
        this.onChooseRepositoryListener = listener
    }

    companion object {

        val TAG = SearchRepositoryBottomSheetDialogFragment::class.java.simpleName

        fun newInstance(): SearchRepositoryBottomSheetDialogFragment {
            return SearchRepositoryBottomSheetDialogFragment()
        }
    }
}
