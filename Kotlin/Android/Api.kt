package com.example.android.apiapp.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET


private const val URL = "www.example.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(URL)
    .build()

interface ApiServices {

    @GET("endpoint")
    suspend fun getThings(): String
}

object Api {
    val retrofitService: ApiServices by lazy { retrofit.create(ApiServices::class.java) }
}