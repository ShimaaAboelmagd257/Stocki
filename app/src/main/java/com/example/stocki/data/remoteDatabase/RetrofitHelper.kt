package com.example.stocki.data.remoteDatabase

import android.util.Log
import com.example.stocki.utility.Constans
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    private val loggingInterceptor = LoggingInterceptor()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Add the logging interceptor
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
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url
        val method = request.method
        val headers = request.headers
        Log.d("StockiRetrofit","API Request: Method=$method, URL=$url, Headers=$headers")
        return chain.proceed(request)
    }
}
