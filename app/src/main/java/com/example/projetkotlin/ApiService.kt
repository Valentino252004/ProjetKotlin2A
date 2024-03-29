package com.example.projetkotlin

import com.example.projetkotlin.retrofit.Dive
import com.example.projetkotlin.retrofit.Member
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/members")
    suspend fun getMembers(): Response<Member>

    @POST("members")
    suspend fun createMember(@Body member: Member): Response<Member>

    @GET("dives")
    suspend fun getDives(): Response<MutableList<Dive>>

    @POST("POST /dives/{diveId}/member/{memberId}")
    suspend fun addMemberToDive(
        @Path("diveId") diveId: Int,
        @Path("memberId") memberId: Int
    ): Response<Dive>

    @PATCH("members/{memberId}")
    suspend fun updateMember(): Response<Member>
}