package com.example.fokus.api

import com.example.fokus.models.LoginResponse
import com.example.fokus.models.Notes
import com.example.fokus.models.SignupResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Headers

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
}