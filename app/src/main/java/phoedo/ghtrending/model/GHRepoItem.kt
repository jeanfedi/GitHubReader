package phoedo.ghtrending.model

import java.io.Serializable

/**
 * Created by phoedo on 24/02/18.
 */

data class GHRepoItem(
        val id: Int,
        val name: String,
        val full_name: String,
        val owner: Owner,
        val private: Boolean,
        val html_url: String,
        val description: String,
        val fork: Boolean,
        val url: String,
        val forks_url: String,
        val keys_url: String,
        val collaborators_url: String,
        val teams_url: String,
        val hooks_url: String,
        val issue_events_url: String,
        val events_url: String,
        val assignees_url: String,
        val branches_url: String,
        val tags_url: String,
        val blobs_url: String,
        val git_tags_url: String,
        val git_refs_url: String,
        val trees_url: String,
        val statuses_url: String,
        val languages_url: String,
        val stargazers_url: String,
        val contributors_url: String,
        val subscribers_url: String,
        val subscription_url: String,
        val commits_url: String,
        val git_commits_url: String,
        val comments_url: String,
        val issue_comment_url: String,
        val contents_url: String,
        val compare_url: String,
        val merges_url: String,
        val archive_url: String,
        val downloads_url: String,
        val issues_url: String,
        val pulls_url: String,
        val milestones_url: String,
        val notifications_url: String,
        val labels_url: String,
        val releases_url: String,
        val deployments_url: String,
        val pushed_at: String?,
        val git_url: String?,
        val ssh_url: String?,
        val clone_url: String?,
        val svn_url: String?,
        val homepage: String?,
        val size: Int?,
        val stargazers_count: Int?,
        val watchers_count: Int?,
        val language: String?,
        val has_issues: Boolean?,
        val has_projects: Boolean?,
        val has_downloads: Boolean?,
        val has_wiki: Boolean?,
        val has_pages: Boolean?,
        val forks_count: Int?,
        val mirror_url: Any?,
        val archived: Boolean?,
        val open_issues_count: Int?,
        val license: License?,
        val forks: Int?,
        val open_issues: Int?,
        val watchers: Int?,
        val default_branch: String?,
        val score: Double?
) : Serializable {
    data class Owner(
            val login: String,
            val id: Int,
            val avatar_url: String,
            val gravatar_id: String,
            val url: String,
            val html_url: String,
            val followers_url: String,
            val following_url: String,
            val gists_url: String,
            val starred_url: String,
            val subscriptions_url: String,
            val organizations_url: String,
            val repos_url: String,
            val events_url: String,
            val received_events_url: String,
            val type: String,
            val site_admin: Boolean
    ) : Serializable

    data class License(
            val key: String?,
            val name: String?,
            val spdx_id: String?,
            val url: String?
    ) : Serializable

}

