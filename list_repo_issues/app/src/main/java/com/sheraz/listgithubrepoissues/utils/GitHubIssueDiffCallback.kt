package com.sheraz.listgithubrepoissues.utils

import androidx.recyclerview.widget.DiffUtil
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem


class GitHubIssueDiffCallback(
    private val newList: List<GitHubRepoIssueItem>,
    private val oldList: List<GitHubRepoIssueItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].id == newList[newItemPosition].id && oldList[oldItemPosition].issueId == newList[newItemPosition].issueId)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].equals(newList[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}