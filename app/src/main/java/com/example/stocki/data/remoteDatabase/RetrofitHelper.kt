package com.example.stocki.data.remoteDatabase

import com.example.stocki.utility.Constans
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer ${Constans.Api_Key}")  // Include API key in the request headers
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }
        .build()
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constans.Base_Url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}
