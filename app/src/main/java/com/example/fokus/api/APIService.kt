package com.example.fokus.api

import com.example.fokus.models.*
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse> // Sends a request with login response body

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/register")
    fun signup(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Call<SignupResponse> // Sends a request with sign up response body

    @GET("/api/fokus_notes")
    fun getNotes(): Call<List<Notes>> // Sends a request of notes in a list

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/fokus_notes")
    fun createNote(
        @Field("title") title: String,
        @Field("content") content: String
    ): Call<Void> // NOT YET IMPLEMENTED

    @GET("/api/task")
    fun getTasks(): Call<List<Task>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/task")
    fun createTask(
        @Field("task_title") taskTitle: String,
    )
}