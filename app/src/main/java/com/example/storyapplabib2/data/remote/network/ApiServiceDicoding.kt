package com.example.storyapplabib2.data.remote.network

import com.example.storyapplabib2.data.remote.payload.PayloadLogin
import com.example.storyapplabib2.data.remote.payload.PayloadRegister
import com.example.storyapplabib2.data.remote.response.DetailStoryResponse
import com.example.storyapplabib2.data.remote.response.LoginResponse
import com.example.storyapplabib2.data.remote.response.PostStoryResponse
import com.example.storyapplabib2.data.remote.response.RegisterResponse
import com.example.storyapplabib2.data.remote.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceDicoding {

    @GET("/v1/stories?location=1")
    fun getStoriesLocation(
    @Query("page") page: Int?=null,
    @Query("size") size: Int?=null,
    ): Call<StoriesResponse>

    @GET("/v1/stories")
    fun getStories(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int = 0
    ): Call<StoriesResponse>

    @GET("/v1/stories/{userId}")
    fun getDetailStory(
        @Path("userId") userId: String
    ): Call<DetailStoryResponse>

    @Multipart
    @POST("/v1/stories")
    fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<PostStoryResponse>


    @Headers("No-Authentication: true")
    @POST("/v1/login")
    fun login(
        @Body payload: PayloadLogin
    ): Call<LoginResponse>

    @Headers("No-Authentication: true")
    @POST("/v1/register")
    fun register(
        @Body payload: PayloadRegister
    ): Call<RegisterResponse>
}