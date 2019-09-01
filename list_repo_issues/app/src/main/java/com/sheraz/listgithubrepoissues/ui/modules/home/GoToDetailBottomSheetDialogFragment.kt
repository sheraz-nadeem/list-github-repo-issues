package com.sheraz.listgithubrepoissues.ui.modules.home

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.databinding.FragmentGoToDetailBottomSheetBinding
import com.sheraz.listgithubrepoissues.di.Injector
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.utils.getFormattedDate
import kotlinx.android.synthetic.main.fragment_go_to_detail_bottom_sheet.*


class GoToDetailBottomSheetDialogFragment : BottomSheetDialogFragment() {

    // Data
    private lateinit var gitHubRepoIssueItem: GitHubRepoIssueItem

    override fun onCreate(savedInstanceState: Bundle?) {
        logger.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {

        logger.d(TAG, "initData: ")
        if (arguments != null && arguments!!.getParcelable<Parcelable>(ARG_REPO_DATA_ITEM) != null) {
            gitHubRepoIssueItem = arguments!!.getParcelable<Parcelable>(ARG_REPO_DATA_ITEM) as GitHubRepoIssueItem
            logger.v(TAG, "initData: gitHubRepoIssueItem: $gitHubRepoIssueItem")
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        logger.d(TAG, "onCreateDialog: ")
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logger.d(TAG, "onCreateView: ")
        val binding = DataBindingUtil.inflate<FragmentGoToDetailBottomSheetBinding>(
            inflater, R.layout.fragment_go_to_detail_bottom_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logger.d(TAG, "onViewCreated: ")

        super.onViewCreated(view, savedInstanceState)

        tvIssueTitle.text = gitHubRepoIssueItem.title
        tvIssueBody.text = gitHubRepoIssueItem.body
        tvIssueState.text = gitHubRepoIssueItem.state
        tvAuthorLogin.text = gitHubRepoIssueItem.authorLogin
        tvAuthorAssociation.text = gitHubRepoIssueItem.authorAssociation
        tvCreatedAt.text = getFormattedDate(gitHubRepoIssueItem.createdAt)

        ibDoneAction.setOnClickListener {
            logger.d(TAG, "ibDoneAction.onClick: ")
            dismiss()
        }
    }

    override fun onResume() {
        logger.d(TAG, "onResume: ")
        super.onResume()
    }

    override fun onPause() {
        logger.d(TAG, "onPause: ")
        super.onPause()
    }

    override fun onDestroy() {
        logger.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        val TAG = GoToDetailBottomSheetDialogFragment::class.java.simpleName
        val logger: Logger = Injector.getCoreComponent().logger()
        private const val ARG_REPO_DATA_ITEM = "arg_repo_issue_item"


        fun newInstance(repoItem: GitHubRepoIssueItem): GoToDetailBottomSheetDialogFragment {
            val fragment = GoToDetailBottomSheetDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_REPO_DATA_ITEM, repoItem)
            fragment.arguments = bundle
            return fragment
        }
    }
}
