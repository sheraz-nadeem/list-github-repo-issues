package com.sheraz.listgithubrepoissues.ui.modules.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.utils.GitHubRepoIssueDiffCallback
import com.sheraz.listgithubrepoissues.utils.getFormattedDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*


class HomeAdapter (
    private val logger: Logger,
    private val mPicasso: Picasso
) : PagedListAdapter<GitHubRepoIssueItem, HomeAdapter.ViewHolder>(GitHubRepoIssueDiffCallback()) {


    private var mListener: View.OnClickListener? = null

    init { logger.d(TAG, "init(): ") }

    override fun onCreateViewHolder(viewGroup: ViewGroup, view_type: Int): ViewHolder {
        logger.d(TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_home, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        logger.d(TAG, "onBindViewHolder: position: $position, currentList.size: ${currentList?.size}")
        viewHolder.bind(getItem(position))
    }

    fun setListener(listener: View.OnClickListener) {
        logger.d(TAG, "setListener: ")
        mListener = listener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(gitHubRepoIssueItem: GitHubRepoIssueItem?) {

            if (gitHubRepoIssueItem == null) return

            setUpViews(gitHubRepoIssueItem)
            handleClicks()

        }

        private fun setUpViews(gitHubRepoIssueItem: GitHubRepoIssueItem?) {

            if (itemView.tag == null) {
                itemView.tag = gitHubRepoIssueItem
            }

            logger.d(TAG, "setUpViews: position: $adapterPosition, title: ${gitHubRepoIssueItem?.title}, authorAvatarUrl: ${gitHubRepoIssueItem?.authorAvatarUrl}")

            mPicasso.load(gitHubRepoIssueItem?.authorAvatarUrl)
                .noFade()
                .placeholder(R.drawable.octocat)
                .into(itemView.ivAuthorAvatar)

            itemView.tvIssueTitle.text = gitHubRepoIssueItem?.title
            itemView.tvIssueBody.text = gitHubRepoIssueItem?.body
            itemView.tvIssueState.text = gitHubRepoIssueItem?.state
            itemView.tvAuthorLogin.text = gitHubRepoIssueItem?.authorLogin
            itemView.tvAuthorAssociation.text = gitHubRepoIssueItem?.authorAssociation
            itemView.tvCreatedAt.text = getFormattedDate(gitHubRepoIssueItem?.createdAt!!)

        }

        private fun handleClicks() {

            itemView.setOnClickListener {

                logger.d(TAG, "OnClickListener: position: $adapterPosition")
                mListener?.onClick(it)

            }
        }

    }

    companion object {
        private val TAG = HomeAdapter::class.java.simpleName
    }
}
