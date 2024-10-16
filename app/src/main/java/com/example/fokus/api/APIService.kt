package com.example.fokus.api

import android.provider.ContactsContract.CommonDataKinds.Note
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

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("/api/FokusApp/{id}")
    fun changeUser(
        @Path("id") id: Int,
        @Field("username") username: String
    ): Call<UserResponse> // Sends a request

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("/api/FokusApp/{id}")
    fun changeEmail(
        @Path("id") id: Int,
        @Field("email") email: String
    ): Call<UserResponse> // Sends a request

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("/api/FokusApp/{id}")
    fun changePassword(
        @Path("id") id: Int,
        @Field("password") password: String
    ): Call<UserResponse> // Sends a request

    // NOTES ROUTES
    @GET("/api/fokus_notes")
    fun getNotes(): Call<List<Notes>> // Sends a request of notes in a list

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/fokus_notes")
    fun createNote(
        @Field("title") title: String,
        @Field("content") content: String
    ): Call<NotesResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("/api/fokus_notes/{id}")
    fun updateNote(
        @Path("id") id: Int,
        @Field("title") title: String,
        @Field("content") content: String
    ): Call<NotesResponse>

    @Headers("Accept: application/json")
    @DELETE("/api/fokus_notes/{id}")
    fun deleteNote(
        @Path("id") id: Int,
    ): Call<NotesResponse>

    // TASK ROUTES
    @GET("/api/task")
    fun getTasks(): Call<List<Task>>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/task")
    fun createTask(
        @Field("task_title") task_title: String,
    ): Call<TaskResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @PUT("/api/task/{id}")
    fun updateTask(
        @Path("id") id: Int,
        @Field("task_title") task_title: String,
    ): Call<TaskResponse>

    @Headers("Accept: application/json")
    @DELETE("/api/task/{id}")
    fun deleteTask(
        @Path("id") id: Int
    ): Call<TaskDeleteResponse>
}