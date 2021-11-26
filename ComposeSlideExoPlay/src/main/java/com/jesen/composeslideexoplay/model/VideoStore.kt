package com.jesen.composeslideexoplay.model

import com.google.gson.annotations.SerializedName

data class VideoStore(
    val adExist: Boolean,
    val count: Int,
    @SerializedName("itemList")
    val videoList: List<VideoItem>?,
    val nextPageUrl: String,
    val total: Int
)

data class VideoItem(
    val adIndex: Int?,
    @SerializedName("data")
    val videoInfo: VideoInfo?,
    val id: Int,
    val tag: String?,
    val trackingData: String?,
    val type: String?
)

data class VideoInfo(
    val ad: Boolean = false,
    val adTrack: List<String>?,
    val author: Author?,
    val brandWebsiteInfo: String?,
    val campaign: String?,
    val category: String?,
    val collected: Boolean = false,
    val consumption: Consumption?,
    val cover: Cover?,
    val dataType: String?,
    val date: Long?,
    val description: String?,
    val descriptionEditor: String?,
    val descriptionPgc: String?,
    val duration: Int?,
    val favoriteAdTrack: String?,
    val id: Int?,
    val idx: Int?,
    val ifLimitVideo: Boolean = false,
    val label: Label? = null,
    val labelList: List<LabelExt>?,
    val lastViewTime: String?,
    val library: String?,
    val playInfo: List<PlayInfo>?,
    val playUrl: String?,
    val played: Boolean = false,
    val playlists: List<PlayInfo>?,
    val promotion: String?,
    val provider: Provider?,
    val reallyCollected: Boolean?,
    val recallSource: String?,
    val recall_source: String?,
    val releaseTime: Long?,
    val remark: String?,
    val resourceType: String?,
    val searchWeight: Int?,
    val shareAdTrack: String?,
    val slogan: String?,
    val src: String?,
    val subtitles: List<String>?,
    val tags: List<Tag>?,
    val thumbPlayUrl: String?,
    val title: String?,
    val titlePgc: String?,
    val type: String?,
    val videoPosterBean: String?,
    val waterMarks: String?,
    val webAdTrack: String?,
    val webUrl: WebUrl?
)

data class Author(
    val adTrack: String?,
    val approvedNotReadyVideoCount: Int,
    val description: String,
    val expert: Boolean,
    val follow: Follow?,
    val icon: String,
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    val name: String,
    val recSort: Int,
    val shield: Shield?,
    val videoNum: Int
)

data class Consumption(
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

data class Cover(
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String?,
    val sharing: String?
)

data class PlayInfo(
    val height: Int,
    val name: String,
    val type: String,
    val url: String,
    val urlList: List<Url>?,
    val width: Int
)


data class Provider(
    val alias: String,
    val icon: String,
    val name: String
)


data class Tag(
    val actionUrl: String,
    val adTrack: String?,
    val bgPicture: String,
    val childTagIdList: List<String>?,
    val childTagList: List<String>?,
    val communityIndex: Int,
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: String?,
    val tagRecType: String
)


data class WebUrl(
    val forWeibo: String,
    val raw: String
)


data class Follow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)


data class Shield(
    val itemId: Int,
    val itemType: String,
    val shielded: Boolean
)


data class Url(
    val name: String,
    val size: Int,
    val url: String
)

data class Label(
    val text: String? = "360°全景",
    val card: String? = "360°全景",
    val detail: String? = "360°全景"
)

data class LabelExt(
    val text: String? = "360°全景",
    val actionUrl: String? = ":null"
)