package com.dipti.sweettweettest.network

import android.text.TextUtils
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "https://api.twitter.com/1.1/"

    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        val defTimeout: Long = 100
        httpClient.connectTimeout(defTimeout, TimeUnit.SECONDS)
        httpClient.readTimeout(defTimeout, TimeUnit.SECONDS)
            .writeTimeout(defTimeout, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val request: Request
            var token = "Bearer AAAAAAAAAAAAAAAAAAAAAOhyGQEAAAAAe%2BVivQyPWzeq0GAKfqhDCkv%2FGiI%3DzRhIVtzn0oiBQj2UV97CLTKQEP8iTZUcgbm3oikFdlryvUYy3Y"
                request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .addHeader("Authorization", token)
                    .method(original.method(), original.body()).build()


            chain.proceed(request)
        }
        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.setLenient()
            .create()

        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}