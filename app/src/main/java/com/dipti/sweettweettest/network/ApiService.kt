package com.dipti.sweettweettest.network

import com.dipti.sweettweettest.model.TweetInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
   @GET("search/tweets.json")
   suspend fun getTweets(@Query("q")  searchKey:String,@Query("result_type") type:String) : TweetInfo
}