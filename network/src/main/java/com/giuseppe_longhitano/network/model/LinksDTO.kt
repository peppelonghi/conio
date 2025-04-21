package com.giuseppe_longhitano.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksDTO(
    val homepage: List<String>,
    val whitepaper: String?,
    @SerialName("blockchain_site") val blockchainSite: List<String>,
    @SerialName("official_forum_url") val officialForumUrl: List<String>,
    @SerialName("chat_url") val chatUrl: List<String>,
    @SerialName("announcement_url") val announcementUrl: List<String>,
    @SerialName("snapshot_url") val snapshotUrl: String?,
    @SerialName("twitter_screen_name") val twitterScreenName: String?,
    @SerialName("facebook_username") val facebookUsername: String?,
    @SerialName("bitcointalk_thread_identifier") val bitcointalkThreadIdentifier: String?,
    @SerialName("telegram_channel_identifier") val telegramChannelIdentifier: String?,
    @SerialName("subreddit_url") val subredditUrl: String?,
    @SerialName("repos_url") val reposUrl: ReposUrlDTO
)