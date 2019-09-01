package com.sheraz.listgithubrepoissues.utils

import androidx.recyclerview.widget.DiffUtil
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoIssueItem

class GitHubRepoIssueDiffCallback : DiffUtil.ItemCallback<GitHubRepoIssueItem>() {

    override fun areContentsTheSame(oldItem: GitHubRepoIssueItem, newItem: GitHubRepoIssueItem): Boolean {
//        return oldItem == newItem
        return (oldItem.id == newItem.id && oldItem.issueId == newItem.issueId)
    }

    override fun areItemsTheSame(oldItem: GitHubRepoIssueItem, newItem: GitHubRepoIssueItem): Boolean {
        return (oldItem.id == newItem.id && oldItem.issueId == newItem.issueId)
    }

}