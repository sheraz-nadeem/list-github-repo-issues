package com.sheraz.listgithubrepoissues.ui.modules.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sheraz.core.utils.Logger
import com.sheraz.listgithubrepoissues.R
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem
import com.sheraz.listgithubrepoissues.utils.GitHubIssueDiffCallback
import com.sheraz.listgithubrepoissues.utils.GitHubRepoDiffCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.llRootContainer
import kotlinx.android.synthetic.main.item_search_repo.view.*


class SearchRepositoryAdapter(
    private val logger: Logger,
    private val mPicasso: Picasso
) : PagedListAdapter<GitHubRepoItem, SearchRepositoryAdapter.ViewHolder>(GitHubRepoDiffCallback()) {


    private var mListener: View.OnClickListener? = null

    init { logger.d(TAG, "init(): ") }

    override fun onCreateViewHolder(viewGroup: ViewGroup, view_type: Int): ViewHolder {
        logger.d(TAG, "onCreateViewHolder: ")
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_search_repo, viewGroup, false)
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

        fun bind(gitHubRepoItem: GitHubRepoItem?) {

            if (gitHubRepoItem == null) return

            setUpViews(gitHubRepoItem)
            handleClicks()
        }

        private fun setUpViews(gitHubRepoItem: GitHubRepoItem?) {

            logger.d(TAG, "setUpViews: position: $adapterPosition, id: ${gitHubRepoItem?.id}, repoId: ${gitHubRepoItem?.repoId}, repoFullName: ${gitHubRepoItem?.fullName}, description: ${gitHubRepoItem?.description}")

            mPicasso.load(gitHubRepoItem?.ownerAvatarUrl)
                .noFade()
                .placeholder(R.drawable.octocat)
                .into(itemView.ivOwnerAvatar)

            itemView.tvRepoName.text = gitHubRepoItem?.name
            itemView.tvOwnerLogin.text = gitHubRepoItem?.ownerLogin
            itemView.tvRepoFullName.text = gitHubRepoItem?.fullName
            itemView.tvRepoDescription.text = gitHubRepoItem?.description

            when (gitHubRepoItem?.fork) {
                true -> {
                    itemView.tvForked.text = "FORKED"
                    itemView.llRootContainer.setBackgroundColor(ContextCompat.getColor(itemView.context, android.R.color.white))
                }
                false -> {
                    itemView.tvForked.text = "NOT FORKED"
                    itemView.llRootContainer.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryVeryLight))
                }
            }

        }

        private fun handleClicks() {

            itemView.setOnClickListener {

                val repoItem = getItem(adapterPosition)
                it.tag = repoItem
                logger.d(TAG, "OnClickListener: position: $adapterPosition, title: ${repoItem?.fullName}, description: ${repoItem?.description}")
                mListener?.onClick(it)

            }
        }

    }

    companion object {
        private val TAG = SearchRepositoryAdapter::class.java.simpleName
    }
}