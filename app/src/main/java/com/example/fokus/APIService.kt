package com.example.fokus

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("/api/FokusApp")
    fun signup(
        @Field("username") username: String,
        @Field("fullname") fullname: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): Call<SignupResponse>
}