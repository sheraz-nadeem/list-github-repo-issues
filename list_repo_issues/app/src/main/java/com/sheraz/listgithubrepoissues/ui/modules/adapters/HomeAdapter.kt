package com.sheraz.listgithubrepoissues.ui.modules.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.utils.getFormattedDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*
import androidx.recyclerview.widget.DiffUtil
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.utils.GitHubIssueDiffCallback


class HomeAdapter (
    private val logger: Logger,
    private val mPicasso: Picasso
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    private var mItems: MutableList<GitHubRepoIssueItem> = mutableListOf()
    private var mListener: View.OnClickListener? = null

    init { logger.d(TAG, "init(): ") }

    override fun onCreateViewHolder(viewGroup: ViewGroup, view_type: Int): ViewHolder {
        logger.d(TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_home, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        logger.d(TAG, "onBindViewHolder: position: $position, mItems.size: ${mItems.size}")
        viewHolder.bind(mItems[position])
    }

    override fun getItemCount(): Int = mItems.size

    fun addItems(items: List<GitHubRepoIssueItem>) {
        mItems.addAll(items)
        logger.d(TAG, "addItems(): newItems.size: ${items.size}, mItems.size: ${mItems.size}")

        notifyDataSetChanged()
    }

    fun updatedItems(items: List<GitHubRepoIssueItem>) {
        val diffResult = DiffUtil.calculateDiff(GitHubIssueDiffCallback(items, mItems))
        diffResult.dispatchUpdatesTo(this)
        logger.d(TAG, "updatedItems(): newItems.size: ${items.size}, mItems.size: ${mItems.size}")
    }

    fun clearItems() {
        logger.d(TAG, "clearItems(): ")
        mItems.clear()
        mItems = mutableListOf()
        notifyDataSetChanged()
    }

    fun setListener(listener: View.OnClickListener) {
        logger.d(TAG, "setListener: ")
        mListener = listener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bind(gitHubRepoIssueItem: GitHubRepoIssueItem) {
            setUpViews(gitHubRepoIssueItem)
            handleClicks()
        }

        private fun setUpViews(gitHubRepoIssueItem: GitHubRepoIssueItem) {

            logger.d(TAG, "setUpViews: position: $adapterPosition, title: ${gitHubRepoIssueItem.title}, authorLogin: ${gitHubRepoIssueItem.authorLogin}")

            mPicasso.load(gitHubRepoIssueItem.authorAvatarUrl)
                .noFade()
                .placeholder(R.drawable.octocat)
                .into(itemView.ivAuthorAvatar)

            itemView.tvIssueTitle.text = gitHubRepoIssueItem.title
            itemView.tvIssueBody.text = gitHubRepoIssueItem.body
            itemView.tvIssueState.text = gitHubRepoIssueItem.state
            itemView.tvAuthorLogin.text = gitHubRepoIssueItem.authorLogin
            itemView.tvAuthorAssociation.text = gitHubRepoIssueItem.authorAssociation
            itemView.tvCreatedAt.text = getFormattedDate(gitHubRepoIssueItem.createdAt)

        }

        private fun handleClicks() {

            itemView.setOnClickListener {
                val issueItem = mItems[adapterPosition]
                it.tag = issueItem
                logger.d(TAG, "OnClickListener: position: $adapterPosition, title: ${issueItem.title}, authorLogin: ${issueItem.authorLogin}")
                mListener?.onClick(it)

            }
        }

    }

    companion object {
        private val TAG = HomeAdapter::class.java.simpleName
    }
}
