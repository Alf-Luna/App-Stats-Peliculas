package com.mooncowpines.kinostats.retrotest

import retrofit2.http.GET
import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)

interface ApiService {
    @GET("posts/1")
    suspend fun getPost(): Post
}