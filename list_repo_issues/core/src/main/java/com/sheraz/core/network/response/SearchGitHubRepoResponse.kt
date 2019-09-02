package com.sheraz.core.network.response

import com.google.gson.annotations.SerializedName
import com.sheraz.core.data.db.entity.GitHubRepoEntity

data class SearchGitHubRepoResponse(

    @SerializedName("items")
    val gitHubRepoList: List<GitHubRepoEntity>

)