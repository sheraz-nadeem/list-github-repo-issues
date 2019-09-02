package com.sheraz.listgithubrepoissues.utils

import androidx.recyclerview.widget.DiffUtil
import com.sheraz.listgithubrepoissues.ui.models.GitHubRepoItem

class GitHubRepoDiffCallback : DiffUtil.ItemCallback<GitHubRepoItem>() {

    override fun areContentsTheSame(oldItem: GitHubRepoItem, newItem: GitHubRepoItem): Boolean {
//        return oldItem == newItem
        return (oldItem.id == newItem.id && oldItem.repoId == newItem.repoId)
    }

    override fun areItemsTheSame(oldItem: GitHubRepoItem, newItem: GitHubRepoItem): Boolean {
        return (oldItem.id == newItem.id && oldItem.repoId == newItem.repoId)
    }

}