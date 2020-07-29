package com.dipti.sweettweettest.repo

import com.dipti.sweettweettest.network.ApiHelper

class TweetRepository (private val apiHelper: ApiHelper) {

    suspend fun getTweets(search : String) = apiHelper.getTweets(search)
}